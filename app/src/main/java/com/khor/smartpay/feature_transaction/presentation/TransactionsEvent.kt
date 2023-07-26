package com.khor.smartpay.feature_transaction.presentation

import com.khor.smartpay.feature_transaction.domain.util.TransactionOrder

sealed class TransactionsEvent {
    object ToggleOrderSection: TransactionsEvent()
    data class Order(val transactionOrder: TransactionOrder): TransactionsEvent()
}