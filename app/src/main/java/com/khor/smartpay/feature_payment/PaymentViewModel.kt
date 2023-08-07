package com.khor.smartpay.feature_payment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(PaymentScreenState())
        private set

    fun onEvent(event: PaymentScreenEvent) {
        when (event) {
            is PaymentScreenEvent.Clear -> state = PaymentScreenState()
            is PaymentScreenEvent.Decimal -> {
                if (!state.amount?.contains(".")!!) {
                    state = state.copy(
                        amount = state.amount + "."
                    )
                }
            }

            is PaymentScreenEvent.Delete -> state = state.copy(amount = state.amount?.dropLast(1))
            is PaymentScreenEvent.DoubleDigitZero -> {
                if (state.amount != null) {
                    state = state.copy(amount = state.amount + "00")
                }
            }

            is PaymentScreenEvent.Number -> {
                state = if (state.amount == null) {
                    state.copy(
                        amount = event.number.toString()
                    )
                } else {
                    if (state.amount!!.length >= MAX_NUM_LENGTH) {
                        return
                    }
                    state.copy(
                        amount = state.amount + event.number
                    )
                }
            }
        }
    }

    companion object {
        private const val MAX_NUM_LENGTH = 10
    }
}