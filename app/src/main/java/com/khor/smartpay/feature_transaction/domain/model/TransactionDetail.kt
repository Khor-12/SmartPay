package com.khor.smartpay.feature_transaction.domain.model

import java.util.Date


data class TransactionDetail(
    val transactionType: String,
    val from: String,
    val to: String,
    val dateTime: String,
    val amount: String
)
