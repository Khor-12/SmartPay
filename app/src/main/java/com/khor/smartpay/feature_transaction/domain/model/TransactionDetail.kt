package com.khor.smartpay.feature_transaction.domain.model

import com.khor.smartpay.feature_transaction.data.local.entity.TransactionDetailEntity


data class TransactionDetail(
    val transactionType: String,
    val from: String,
    val to: String,
    val dateTime: String,
    val amount: String
) {
    fun toTransactionEntity(): TransactionDetailEntity {
        return TransactionDetailEntity(
            transactionType = transactionType,
            from = from,
            to = to,
            dateTime = dateTime,
            amount = amount
        )
    }
}

