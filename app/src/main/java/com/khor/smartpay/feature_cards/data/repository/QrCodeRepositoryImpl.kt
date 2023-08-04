package com.khor.smartpay.feature_cards.data.repository

import com.google.firebase.firestore.FieldValue
import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.core.util.toSimpleDate
import com.khor.smartpay.feature_auth.domain.model.Card
import com.khor.smartpay.feature_auth.domain.model.SmartUser
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_cards.domain.repository.QrCodeRepository
import com.khor.smartpay.feature_transaction.domain.model.TransactionDetail
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QrCodeRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository
) : QrCodeRepository {
    private val db = authRepository.db!!
    private val userUid = authRepository.currentUser!!.uid

    override suspend fun createCard(): Flow<Resource<Boolean>> = callbackFlow {
        send(Resource.Loading(true))

        authRepository.startScanning().collect { qrCode ->
            qrCode?.let {
                val cardReference = db.collection("Cards").document(it)
                val userDocument = db.collection("users").document(authRepository.currentUser!!.uid)

                cardReference.get()
                    .addOnSuccessListener { cardSnapshot ->
                        if (cardSnapshot.exists()) {
                            // Card exists
                            launch { send(Resource.Error("QrCode Card already exists")) }
                        } else {
                            // create the card
                            userDocument.update("qrCodes", FieldValue.arrayUnion(qrCode))
                                .addOnSuccessListener {
                                    // navigate to Home screen
                                    cardReference.set(Card(qrCode = qrCode))
                                    launch {
                                        send(Resource.Success(true))
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    launch {
                                        send(Resource.Error("Failed to create user ${exception.message}"))
                                    }
                                }
                        }
                    }
                    .addOnFailureListener { }
            }
        }

        awaitClose { }
    }

    override suspend fun getQrCodeCards(): Flow<Resource<List<Card>>> = callbackFlow {
        val userDocument = db.collection("users").document(userUid)
            .get()
            .addOnSuccessListener { userSnapshot ->
                val arrayField = userSnapshot.get("qrCodes") as? List<String>
                val cards = mutableListOf<Card>()

                arrayField?.forEach { c ->
                    db.collection("Cards").document(c)
                        .get()
                        .addOnSuccessListener { document ->
                            cards.add(
                                Card(
                                    qrCode = document.getString("qrCode") as String,
                                    isFrozen = document.getBoolean("frozen") as Boolean,
                                    limit = document.getDouble("limit") as Double
                                )
                            )
                            // Emit the success response for each document retrieval
                            launch { send(Resource.Success(cards.toList())) }
                        }
                        .addOnFailureListener { exception ->
                            // Handle failure if necessary
                            launch { send(Resource.Error(exception.message ?: "Unknown error")) }
                        }
                }

                // Close the callbackFlow once all Firestore queries are done
            }
            .addOnFailureListener { exception ->
                // Handle failure if necessary
                launch { send(Resource.Error(exception.message ?: "Unknown error")) }
                close()
            }

        // Use awaitClose to properly handle closing the callbackFlow
        awaitClose { }
    }

    override suspend fun updateCard(cardId: String, isFrozen: Boolean?, limit: Double?): Flow<Resource<Boolean>> = callbackFlow {
        send(Resource.Loading(true))

        if (isFrozen != null) {
            db.collection("Cards")
                .document(cardId)
                .update("frozen", isFrozen)
                .addOnSuccessListener {
                    // Update successful, you can add further logic here if needed.
                    // For example, display a toast or show a SnackBar.
                    launch {
                        send(Resource.Success(true))
                    }

                }
                .addOnFailureListener {
                    // Update failed, handle the error here.
                    // For example, display a toast or show a SnackBar with the error message.
                    launch {
                        send(Resource.Error("$it"))
                    }
                }
        }


        if (limit != null) {
            db.collection("Cards")
                .document(cardId)
                .update("limit", limit)
                .addOnSuccessListener {
                    // Update successful, you can add further logic here if needed.
                    // For example, display a toast or show a SnackBar.
                    launch {
                        send(Resource.Success(true))
                    }

                }
                .addOnFailureListener {
                    // Update failed, handle the error here.
                    // For example, display a toast or show a SnackBar with the error message.
                    launch {
                        send(Resource.Error("$it"))
                    }
                }

        }

        awaitClose {  }
    }

    override suspend fun deleteCard(card: String): Flow<Resource<Boolean>> = callbackFlow {
        db.collection("Cards").document(card)
            .delete()
            .addOnSuccessListener {
                // Document deleted successfully
                // Handle success if needed
                val documentRef = db.collection("users").document(userUid)

                // Update the document to remove the specified value from the array field
                documentRef.update("qrCodes", FieldValue.arrayRemove(card))
                    .addOnSuccessListener {
                        // Handle success
                        launch {
                            send(Resource.Success(true))
                        }
                    }
                    .addOnFailureListener {
                        // Handle error
                    }
            }
            .addOnFailureListener {
                // Handle delete failure, display an error message if needed
            }

        awaitClose {  }
    }

}
