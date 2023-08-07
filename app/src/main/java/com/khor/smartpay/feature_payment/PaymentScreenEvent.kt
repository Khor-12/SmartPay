package com.khor.smartpay.feature_payment

sealed class PaymentScreenEvent {
    data class Number(val number: Int): PaymentScreenEvent()
    object DoubleDigitZero: PaymentScreenEvent()
    object Clear: PaymentScreenEvent()
    object Delete:  PaymentScreenEvent()
    object Decimal: PaymentScreenEvent()
}