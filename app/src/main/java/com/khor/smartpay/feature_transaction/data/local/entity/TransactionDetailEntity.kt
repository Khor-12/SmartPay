package com.khor.smartpay.feature_transaction.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khor.smartpay.feature_transaction.domain.model.TransactionDetail

@Entity
data class TransactionDetailEntity(
    val transactionType: String,
    val from: String,
    val to: String,
    val dateTime: String,
    val amount: String,
    @PrimaryKey val id: Int? = null
) {
    fun toTransactionDetail(): TransactionDetail {
        return TransactionDetail(
            transactionType = transactionType,
            from = from,
            to = to,
            dateTime = dateTime,
            amount = amount
        )
    }
}
