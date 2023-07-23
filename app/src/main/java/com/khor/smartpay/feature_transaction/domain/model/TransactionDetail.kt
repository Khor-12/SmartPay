package com.khor.smartpay.feature_transaction.domain.model

import com.khor.smartpay.feature_transaction.data.remote.dto.TransactionType

data class Transaction(
    val transactionType: TransactionType,
    val from: String,
    val to: String,
    val dateTime: String,
    val amount: Double
)
