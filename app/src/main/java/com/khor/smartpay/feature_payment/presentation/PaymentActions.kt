package com.khor.smartpay.feature_payment.presentation

import com.khor.smartpay.feature_payment.domain.model.PaymentAction
import com.khor.smartpay.feature_payment.presentation.components.PaymentButton

val paymentActions = listOf(
    PaymentUiAction(
        text = "7",
        action = PaymentAction.Number(1),
    ),
    PaymentUiAction(
        text = "8",
        action = PaymentAction.Number(2)
    ),
    PaymentUiAction(
        text = "9",
        action = PaymentAction.Number(3)
    ),
    PaymentUiAction(
        text = "4",
        action = PaymentAction.Number(4)
    ),
    PaymentUiAction(
        text = "5",
        action = PaymentAction.Number(5)
    ),
    PaymentUiAction(
        text = "6",
        action = PaymentAction.Number(6)
    ),
    PaymentUiAction(
        text = "1",
        action = PaymentAction.Number(7)
    ),
    PaymentUiAction(
        text = "2",
        action = PaymentAction.Number(8)
    ),
    PaymentUiAction(
        text = "3",
        action = PaymentAction.Number(9)
    ),
    PaymentUiAction(
        text = "00",
        action = PaymentAction.Number(0)
    ),
    PaymentUiAction(
        text = "0",
        action = PaymentAction.DoubleDigit("00")
    ),
    PaymentUiAction(
        text = ".",
        action = PaymentAction.Decimal
    )
)