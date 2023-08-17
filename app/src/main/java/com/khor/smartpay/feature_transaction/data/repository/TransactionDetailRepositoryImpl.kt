package com.khor.smartpay.feature_transaction.data.repository

import android.util.Log
import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.core.util.toSimpleDate
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_transaction.data.local.TransactionDetailDao
import com.khor.smartpay.feature_transaction.data.local.entity.TransactionDetailEntity
import com.khor.smartpay.feature_transaction.domain.model.TransactionDetail
import com.khor.smartpay.feature_transaction.domain.repository.TransactionDetailRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject

class TransactionDetailRepositoryImpl @Inject constructor(
    private val dao: TransactionDetailDao,
    private val authRepository: AuthRepository
) : TransactionDetailRepository {

    override suspend fun getTransactions(): Flow<Resource<List<TransactionDetail>>> = flow {
        val db = authRepository.db!!
        val currentUserUid = authRepository.currentUser!!.uid

        emit(Resource.Loading(true))

        try {
            val snapshot = db.collection("users")
                .document(currentUserUid)
                .collection("Transactions").get().await() // Wait for Firestore query to complete

            val transactions = mutableListOf<TransactionDetail>()

            for (document in snapshot.documents) {
                transactions.add(
                    TransactionDetail(
                        transactionType = document.getString("transactionType") ?: "",
                        from = document.getString("from") ?: "",
                        to = document.getString("to") ?: "",
                        amount = document.getString("amount") ?: "",
                        dateTime = document.getString("dateTime")?.toSimpleDate() ?: ""
                    )
                )
            }

            // Insert data into Room database using suspend function
            dao.deleteTransactions()
            dao.insertTransactionsDetails(transactions.map { it.toTransactionEntity() })

            // Emit success with data from the Room database
            emit(Resource.Success(data = dao.getTransactions().map { it.toTransactionDetail() }))
        } catch (e: Exception) {
            // Emit failure if any error occurs
            emit(Resource.Error(e.localizedMessage))
            emit(Resource.Success(data = dao.getTransactions().map { it.toTransactionDetail() }))
        }
    }

}
