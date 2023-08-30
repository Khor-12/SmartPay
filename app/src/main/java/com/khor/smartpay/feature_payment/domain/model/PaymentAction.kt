package com.khor.smartpay.feature_payment.domain.model

sealed interface PaymentAction {
    data class Number(val number: Int): PaymentAction
    data class DoubleDigit(val doubleDigit: String): PaymentAction
    object Clear: PaymentAction
    object Delete: PaymentAction
    object Decimal: PaymentAction
}