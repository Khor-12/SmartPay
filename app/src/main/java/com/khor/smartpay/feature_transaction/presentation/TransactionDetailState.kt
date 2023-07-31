package com.khor.smartpay.feature_transaction.presentation

import com.khor.smartpay.feature_transaction.domain.model.TransactionDetail
import com.khor.smartpay.feature_transaction.domain.util.TransactionOrder

data class TransactionDetailState(
    val transactionDetailItems: List<TransactionDetail> = emptyList(),
    val isLoading: Boolean = false,
    val isFilterListVisible: Boolean = false,
    var transactionOrder: TransactionOrder = TransactionOrder.All,
    val isRefreshing: Boolean = false
)
