package com.khor.smartpay.feature_transaction.presentation

import com.khor.smartpay.feature_transaction.domain.model.TransactionDetail

data class TransactionDetailState(
    val transactionDetailItems: List<TransactionDetail> = emptyList(),
    val isLoading: Boolean = false
)
