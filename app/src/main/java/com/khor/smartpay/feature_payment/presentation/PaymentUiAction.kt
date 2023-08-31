package com.khor.smartpay.feature_payment.presentation

import androidx.compose.runtime.Composable


data class PaymentUiAction(
    val text: String?,
    val action: PaymentScreenEvent,
    val content: @Composable () -> Unit = {}
)