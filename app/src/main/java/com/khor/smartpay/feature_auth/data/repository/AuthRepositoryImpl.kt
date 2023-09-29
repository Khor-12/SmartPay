package com.khor.smartpay.feature_auth.data.repository

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.core.util.getCurrentTime
import com.khor.smartpay.feature_auth.data.local.UserPreferencesDao
import com.khor.smartpay.feature_auth.domain.model.Card
import com.khor.smartpay.feature_auth.domain.model.SmartUser
import com.khor.smartpay.feature_auth.domain.model.UserPreferences
import com.khor.smartpay.feature_auth.domain.model.UserSell
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_auth.domain.repository.AuthStateResponse
import com.khor.smartpay.feature_transaction.data.local.TransactionDetailDao
import com.khor.smartpay.feature_transaction.domain.model.TransactionDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val scanner: GmsBarcodeScanner,
    private val firestore: FirebaseFirestore,
    private val dao: UserPreferencesDao
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
        val user = SmartUser(qrCodes = listOf(qrCode), userType = "qr")

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
                                        userDocument.collection("Transactions")
                                        cardReference.set(
                                            Card(
                                                qrCode = qrCode,
                                                userId = currentUser!!.uid
                                            )
                                        )
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

    override suspend fun getCurrentReference(): Flow<Resource<Double>> = callbackFlow {
        val docRef = db.collection("Reference").document("smart-pay-deposit-reference-count")

        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val fieldValue = documentSnapshot.getDouble("ReferenceCount")
                    if (fieldValue != null) {
                        launch {
                            send(Resource.Success(fieldValue))
                            docRef.update("ReferenceCount", fieldValue + 1)
                        }
                    } else { /* field not found */
                    }
                } else { /* Document not found */
                }
            }
            .addOnFailureListener { }

        awaitClose { }
    }

    override suspend fun makeDeposit(amount: Double, phoneNumber: String) {
        val currentUserTransactionsRef =
            db.collection("users").document(currentUser!!.uid).collection("Transactions")

        currentUserTransactionsRef.add(
            TransactionDetail(
                transactionType = "DEPOSIT",
                from = phoneNumber,
                to = "SmartPayAccount",
                dateTime = getCurrentTime(),
                amount = amount.toString()
            )
        ).addOnSuccessListener {}
            .addOnFailureListener {}
    }

    override suspend fun checkUserExistenceInDb(): Flow<Resource<Boolean>> = callbackFlow {

        val userDocument = db.collection("users").document(auth.currentUser?.uid!!)

        userDocument.get()
            .addOnSuccessListener { userSnapshot ->
                launch { send(Resource.Loading(true)) }
                if (userSnapshot.exists()) {
                    // login the user
                    launch {
                        send(Resource.Loading(false))
                    }
                    launch {
                        send(Resource.Success(true))
                    }
                } else {
                    // creating the user
                    launch {
                        send(Resource.Success(false))
                    }
                }
            }

        awaitClose { }
    }

    override suspend fun createUserWithCode(pinCode: String): Flow<Resource<String>> =
        callbackFlow {

            val userDocument = db.collection("users").document(auth.currentUser!!.uid)
            val userSell = UserSell(pinCode = pinCode, userType = "pin")

            send(Resource.Loading(true))
            userDocument.set(userSell)
                .addOnSuccessListener {
                    launch {
                        send(Resource.Loading(false))
                    }
                    launch {
                        send(Resource.Success("User successfully created"))
                    }
                }
                .addOnFailureListener { }


            awaitClose { }
        }

    override suspend fun loginUserWithCode(pinCode: String): Flow<Resource<String>> = callbackFlow {
        val userDocument = db.collection("users").document(auth.currentUser!!.uid)

        send(Resource.Loading(true))

        userDocument.get()
            .addOnSuccessListener { userSnapshot ->
                if (userSnapshot.exists()) {
                    val userPinCode = userSnapshot.getString("pinCode")
                    if (pinCode == userPinCode) {
                        // login user
                        launch {
                            send(Resource.Success("login successfully"))
                        }
                    } else {
                        // register
                        launch {
                            send(Resource.Error("Invalid credential"))
                        }
                    }
                } else {
                    // document doesn't exits
                }
            }

        awaitClose { }
    }

    override suspend fun checkUserType(): Flow<Resource<String>> = callbackFlow {
        val userDocument = db.collection("users").document(auth.currentUser?.uid!!)

        userDocument.get()
            .addOnSuccessListener { userSnapshot ->
                launch { send(Resource.Loading(true)) }
                if (userSnapshot.exists()) {
                    // login the user
                    val currentUserType = userSnapshot.getString("userType")
                    if (currentUserType != null) {
                        launch { send(Resource.Success(currentUserType)) }
                    }
                } else {
                    // creating the user
                    launch {
                        send(Resource.Error("User doesn't exists"))
                    }
                }
            }

        awaitClose { }
    }


    override fun getAuthState(
        viewModelScope: CoroutineScope
    ): AuthStateResponse = callbackFlow {
        val userVerified = withContext(Dispatchers.IO) {
            dao.isUserVerified()
        }
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null && userVerified ?: false)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        auth.currentUser != null && runBlocking(Dispatchers.IO) {
            dao.isUserVerified() ?: false
        }
    )


    override suspend fun updateUserPreferences(pref: UserPreferences): Flow<Boolean> = flow {
        val userPreferenceEntity = pref.toUserPreferenceEntity()
        try {
            withContext(Dispatchers.IO) {
                dao.insertPreference(userPreferenceEntity)
            }
            println("Insertion was successful, emit true")
            // Insertion was successful, emit true
            emit(true)
        } catch (e: Exception) {
            // Insertion failed, emit false
            println("Insertion failed emit false $e")
            emit(false)
        }
    }


    override suspend fun getUserVerificationStatus(): Flow<Boolean?> = flow {
        val userVerified = withContext(Dispatchers.IO) {
            dao.isUserVerified()
        }
        emit(userVerified)
    }

    override suspend fun getUserType(): Flow<String?> = flow {
        val userType = withContext(Dispatchers.IO) {
            dao.getUserType()
        }
        emit(userType)
    }

    override fun signOut() = auth.signOut()

    override suspend fun performDeleteAccount(): Flow<Resource<Boolean>> = callbackFlow {
        val userRef = db.collection("users").document(currentUser?.uid ?: "")
        val batch = db.batch()

        db.collection("users").document(currentUser!!.uid)
            .get()
            .addOnSuccessListener { userSnapshot ->
                val arrayField = userSnapshot.get("qrCodes") as? List<String>

                arrayField?.forEach { c ->
                    val cardRef = db.collection("Cards").document(c)
                    batch.delete(cardRef)
                }

                // Commit the batch operation
                batch.commit()
                    .addOnSuccessListener {
                        // Batch deletion successful
                        userRef.delete()
                            .addOnSuccessListener {
                                // User document deletion successful
                                currentUser?.delete()
                                    ?.addOnSuccessListener {
                                        // User account deletion successful
                                        launch {
                                            send(Resource.Success(true))
                                        }
                                    }
                                    ?.addOnFailureListener { exception ->
                                        // Handle user account deletion failure
                                        launch {
                                            send(Resource.Error(exception.message.toString()))
                                        }
                                    }
                            }
                            .addOnFailureListener { exception ->
                                // Handle user document deletion failure
                                launch {
                                    send(Resource.Error(exception.message.toString()))
                                }
                            }
                    }
                    .addOnFailureListener { exception ->
                        // Handle batch deletion failure
                        launch {
                            send(Resource.Error(exception.message.toString()))
                        }
                    }
            }
            .addOnFailureListener { exception ->
                // Handle user document retrieval failure
                launch {
                    send(Resource.Error(exception.message.toString()))
                }
            }

        awaitClose { }
    }

}