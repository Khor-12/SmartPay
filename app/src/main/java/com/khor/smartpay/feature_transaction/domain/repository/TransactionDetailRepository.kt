package com.khor.smartpay.feature_transaction.domain.repository

import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.feature_transaction.domain.model.TransactionDetail
import com.khor.smartpay.feature_transaction.domain.util.TransactionOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface TransactionDetailRepository {

    suspend fun getTransactions(): Flow<Resource<List<TransactionDetail>>>
}