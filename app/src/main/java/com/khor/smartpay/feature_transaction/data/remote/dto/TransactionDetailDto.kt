package com.khor.smartpay.feature_transaction.data.remote.dto

import com.khor.smartpay.feature_transaction.domain.model.Transaction

data class TransactionDetail(
    val transactionType: TransactionType,
    val from: String,
    val to: String,
    val dateTime: String,
    val amount: Double
) {
    fun toTransaction(): Transaction {
        return Transaction(
            transactionType = transactionType,
            from = from,
            to = to,
            dateTime = dateTime,
            amount = amount
        )
    }
}
