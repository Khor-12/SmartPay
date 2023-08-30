package com.khor.smartpay.feature_payment.presentation

import androidx.compose.runtime.Composable
import com.khor.smartpay.feature_payment.domain.model.PaymentAction


data class PaymentUiAction(
    val text: String?,
    val action: PaymentAction,
    val content: @Composable () -> Unit = {}
)