package com.khor.smartpay.feature_payment.data.repository

import androidx.compose.runtime.collectAsState
import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.core.util.getCurrentTime
import com.khor.smartpay.core.util.toSimpleDate
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_payment.domain.repository.PaymentRepository
import com.khor.smartpay.feature_transaction.domain.model.TransactionDetail
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class PaymentRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository
) : PaymentRepository {
    private val db = authRepository.db!!
    private val userUid = authRepository.currentUser!!.uid

    override suspend fun makePayment(amount: Double): Flow<Resource<String>> = callbackFlow {

        authRepository.startScanning().collect { qrCode ->
            qrCode?.let { qrCodeValue ->
                send(Resource.Loading(true))

                db.collection("Cards").document(qrCodeValue).get()
                    .addOnSuccessListener { cardSnapShot ->
                        if (cardSnapShot.exists()) {
                            // Only the transaction when th card exists
                            val isFrozen = cardSnapShot.getBoolean("frozen") as Boolean
                            val limit = cardSnapShot.getDouble("limit") ?: 0.0
                            val userId = cardSnapShot.getString("userId") as String

                            launch {
                                if (isFrozen) {
                                    launch {
                                        // The Card is frozen you cannot make any purchases
                                        send(Resource.Error(message = "The customer's card is frozen, it can't make any transactions."))
                                    }
                                } else {
                                    getTotalBalance(userUid = userId).collect { userBalance ->
                                        if (userBalance >= amount) {
                                            if (amount <= limit) {
                                                // The Card has enough money
                                                makeTransaction(
                                                    customerUid = userId,
                                                    amount = amount,
                                                    qrCodeValue = qrCodeValue
                                                ).collect {
                                                    if (it) {
                                                        send(Resource.Success("The transaction was successful"))
                                                    }
                                                }
                                            } else if (limit == 0.0) {
                                                // The Card has no limit
                                                makeTransaction(
                                                    customerUid = userId,
                                                    amount = amount,
                                                    qrCodeValue = qrCodeValue
                                                ).collect {
                                                    if (it) {
                                                        send(Resource.Success("The transaction was successful"))
                                                    }
                                                }
                                            } else {
                                                send(Resource.Error(message = "The amount is beyond the limit"))
                                            }
                                        } else {
                                            // User has insufficient money in his account
                                            send(Resource.Error(message = "The customer has insufficient money in his/her account"))
                                        }
                                    }
                                }
                            }
                        } else {
                            launch {
                                send(Resource.Error(message = "The card doesn't exists"))
                            }
                        }

                    }.addOnFailureListener {}
            }
        }

        awaitClose { }
    }

    override suspend fun getTotalBalance(userUid: String): Flow<Double> = callbackFlow {
        var amount = 0.0

        val snapshot =
            db.collection("users").document(userUid).collection("Transactions").get().await()

        if (snapshot.isEmpty) {
            send(amount)
        } else {
            for (document in snapshot.documents) {
                amount += document.getString("amount")!!.toDouble()
            }
        }

        send(amount)

        awaitClose { }
    }

    private suspend fun makeTransaction(
        customerUid: String,
        qrCodeValue: String,
        amount: Double
    ): Flow<Boolean> = callbackFlow {
        val currentUserTransactionsRef =
            db.collection("users").document(userUid).collection("Transactions")
        val customerTransactionRef =
            db.collection("users").document(customerUid).collection("Transactions")

        customerTransactionRef.add(
            TransactionDetail(
                transactionType = "BUY",
                from = authRepository.currentUser!!.phoneNumber.toString(),
                to = qrCodeValue,
                dateTime = getCurrentTime(),
                amount = (-amount).toString()
            )
        ).addOnSuccessListener {
            currentUserTransactionsRef.add(
                TransactionDetail(
                    transactionType = "SELL",
                    from = authRepository.currentUser!!.phoneNumber.toString(),
                    to = qrCodeValue,
                    dateTime = getCurrentTime(),
                    amount = amount.toString()
                )
            ).addOnSuccessListener {
                launch {
                    send(true)
                }
            }.addOnFailureListener {
                launch {
                    send(false)
                }
            }
        }.addOnFailureListener {
            launch {
                send(false)
            }
        }

        awaitClose { }

    }


}