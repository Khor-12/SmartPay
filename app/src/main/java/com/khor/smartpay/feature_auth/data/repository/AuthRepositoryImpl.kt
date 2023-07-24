package com.khor.smartpay.feature_auth.data.repository

import android.app.Activity
import androidx.compose.runtime.MutableState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.feature_auth.domain.model.Card
import com.khor.smartpay.feature_auth.domain.model.SmartUser
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_auth.domain.repository.AuthStateResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpRetryException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val scanner: GmsBarcodeScanner,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override val currentUser
        get() = auth.currentUser
    override val db: FirebaseFirestore
        get() = firestore

    override suspend fun sendVerificationCode(
        number: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options =
            PhoneAuthOptions.newBuilder(auth).setPhoneNumber(number) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        activity: Activity,
        signInWithCredential: (Task<AuthResult>) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                signInWithCredential(task)
            }
    }

    override suspend fun createUser(qrCode: String): Flow<Resource<String>> = callbackFlow {
        val userId = currentUser!!.uid
        val usersCollection = db.collection("users")
        val userDocument = usersCollection.document(userId)
        val user = SmartUser(qrCodes = listOf(qrCode))

        val cardReference = db.collection("Cards").document(qrCode)

        userDocument.get()
            .addOnSuccessListener { userSnapshot ->
                launch { send(Resource.Loading(true)) }
                if (userSnapshot.exists()) {
                    // login the user
                    val arrayField = userSnapshot.get("qrCodes") as? List<String>
                    if (arrayField != null && arrayField.contains(qrCode)) {
                        launch {
                            // navigate to home screen
                            send(Resource.Success("Successful"))
                        }
                        // Handle the case where the value exists in the array field.
                    } else {
                        launch {
                            send(Resource.Error("Invalid credentials"))
                        }
                        // Handle the case where the value does not exist in the array field.
                    }
                } else {
                    // creating the user
                    cardReference.get()
                        .addOnSuccessListener { cardSnapshot ->
                            if (cardSnapshot.exists()) {
                                launch { send(Resource.Error("QrCode Card already exists")) }
                                // Handle the case where the document exists.
                            } else {
                                userDocument.set(user)
                                    .addOnSuccessListener {
                                        // navigate to Home screen
                                        cardReference.set(Card(qrCode = qrCode))
                                        launch {
                                            send(Resource.Success("User $userId created successfully."))
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        launch {
                                            send(Resource.Error("Failed to create user $userId: ${exception.message}"))
                                        }
                                    }
                            }
                        }
                        .addOnFailureListener { exception ->
                            launch {
                                send(Resource.Error("Error checking document existence: $exception"))
                            }
                        }

                }
            }
            .addOnFailureListener { exception ->
                launch {
                    send(Resource.Error("Error checking user existence for $userId: ${exception.message}"))
                }
            }


        awaitClose { }
    }

    override fun startScanning(): Flow<String?> {
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener {
                    launch {
                        send(it.rawValue)
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                }
            awaitClose { }
        }

    }

    override fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse =
        callbackFlow {
            val authStateListener = FirebaseAuth.AuthStateListener { auth ->
                trySend(auth.currentUser == null)
            }
            auth.addAuthStateListener(authStateListener)
            awaitClose {
                auth.removeAuthStateListener(authStateListener)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            auth.currentUser == null
        )
}