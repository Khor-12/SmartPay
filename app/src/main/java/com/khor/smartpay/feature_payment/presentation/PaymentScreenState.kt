package com.khor.smartpay.feature_payment.presentation

import androidx.compose.runtime.MutableState

data class PaymentScreenState(
    val amount: String? = null,
    val totalBalance: Double = 0.0,
    val transactionIsLoading: Boolean = false,
    val transactionError: Boolean = false,
    val transactionMessage: String = "",
    val showAlertDialog: Boolean = false
)
