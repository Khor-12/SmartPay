package com.khor.smartpay.feature_payment.presentation

val paymentActions = listOf(
    PaymentUiAction(
        text = "7",
        action = PaymentScreenEvent.Number(7),
    ),
    PaymentUiAction(
        text = "8",
        action = PaymentScreenEvent.Number(8)
    ),
    PaymentUiAction(
        text = "9",
        action = PaymentScreenEvent.Number(9)
    ),
    PaymentUiAction(
        text = "4",
        action = PaymentScreenEvent.Number(4)
    ),
    PaymentUiAction(
        text = "5",
        action = PaymentScreenEvent.Number(5)
    ),
    PaymentUiAction(
        text = "6",
        action = PaymentScreenEvent.Number(6)
    ),
    PaymentUiAction(
        text = "1",
        action = PaymentScreenEvent.Number(1)
    ),
    PaymentUiAction(
        text = "2",
        action = PaymentScreenEvent.Number(2)
    ),
    PaymentUiAction(
        text = "3",
        action = PaymentScreenEvent.Number(3)
    ),
    PaymentUiAction(
        text = "00",
        action = PaymentScreenEvent.DoubleDigitZero
    ),
    PaymentUiAction(
        text = "0",
        action = PaymentScreenEvent.Number(0)
    ),
    PaymentUiAction(
        text = ".",
        action = PaymentScreenEvent.Decimal
    )
)