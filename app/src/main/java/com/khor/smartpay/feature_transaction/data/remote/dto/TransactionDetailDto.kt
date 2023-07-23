package com.khor.smartpay.feature_transaction.data.remote.dto

import com.khor.smartpay.feature_transaction.data.local.entity.TransactionDetailEntity

data class TransactionDetailDto(
    val transactionType: String,
    val from: String,
    val to: String,
    val dateTime: String,
    val amount: String
) {
    fun toTransactionDetailEntity(): TransactionDetailEntity {
        return TransactionDetailEntity(
            transactionType = transactionType,
            from = from,
            to = to,
            dateTime = dateTime,
            amount = amount
        )
    }
}
