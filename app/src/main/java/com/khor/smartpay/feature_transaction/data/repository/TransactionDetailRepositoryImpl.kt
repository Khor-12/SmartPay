package com.khor.smartpay.feature_transaction.data.repository

import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_transaction.data.local.TransactionDetailDao
import com.khor.smartpay.feature_transaction.data.local.entity.TransactionDetailEntity
import com.khor.smartpay.feature_transaction.domain.model.TransactionDetail
import com.khor.smartpay.feature_transaction.domain.repository.TransactionDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TransactionDetailRepositoryImpl @Inject constructor(
    private val dao: TransactionDetailDao,
    private val authRepository: AuthRepository
) : TransactionDetailRepository {

    override suspend fun getTransactions(): Flow<Resource<List<TransactionDetail>>> = callbackFlow {
        val db = authRepository.db!!
        val currentUserUid = authRepository.currentUser!!.uid
        val transactions = mutableListOf<TransactionDetail>()

        val transactionsDetails = dao.getTransactions().map { it.toTransactionDetail() }
        send(Resource.Loading(data = transactionsDetails))

        db.collection("users")
            .document(currentUserUid)
            .collection("Transactions").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        launch {
                            transactions.add(
                                TransactionDetail(
                                    transactionType = document.get("transactionType") as String,
                                    from = document.get("from") as String,
                                    to = document.get("to") as String,
                                    amount = document.getDouble("amount").toString(),
                                    dateTime = document.getTimestamp("dateTime")!!.toDate()
                                        .toString()
                                )
                            )
                            send(
                                Resource.Success(
                                    transactions
                                )
                            )
                        }
                        launch {
                            dao.deleteTransactions()
                            dao.insertTransactionsDetails(
                                listOf(
                                    TransactionDetailEntity(
                                        transactionType = document.get("transactionType") as String,
                                        from = document.get("from") as String,
                                        to = document.get("to") as String,
                                        amount = document.getDouble("amount").toString(),
                                        dateTime = document.getTimestamp("dateTime").toString()
                                    )
                                )
                            )
                        }
                    }
                }
            }
            .addOnFailureListener {
                launch {
                    send(
                        Resource.Error("Unknown Error")
                    )
                }
            }

        awaitClose { }
    }
}
