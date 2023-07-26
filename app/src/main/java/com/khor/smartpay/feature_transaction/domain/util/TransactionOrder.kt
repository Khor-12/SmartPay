package com.khor.smartpay.feature_transaction.domain.util

sealed class TransactionOrder {
    object All : TransactionOrder()
    object Buy : TransactionOrder()
    object Sell : TransactionOrder()
    object Deposit : TransactionOrder()
    object Withdraw : TransactionOrder()
}
