package com.khor.smartpay.feature_payment.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.feature_payment.domain.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    var state by mutableStateOf(PaymentScreenState())
        private set

    fun onEvent(event: PaymentScreenEvent) {
        when (event) {
            is PaymentScreenEvent.ChangeAlert -> state = state.copy(
                showAlertDialog = event.state
            )
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

    fun makePayment() {
        if (state.amount != null && state.amount!!.toDouble() > 0) {
            viewModelScope.launch {
                paymentRepository.makePayment(state.amount!!.toDouble())
                    .onEach { result ->
                        when (result) {
                            is Resource.Loading -> state = state.copy(
                                transactionIsLoading = true
                            )

                            is Resource.Error -> state = state.copy(
                                transactionError = true,
                                transactionMessage = result.message ?: "",
                                transactionIsLoading = false,
                                showAlertDialog = true
                            )

                            is Resource.Success -> state = state.copy(
                                transactionError = false,
                                transactionMessage = result.data ?: "",
                                transactionIsLoading = false,
                                showAlertDialog = true
                            )
                        }
                    }.launchIn(this)
            }
        }
    }

    companion object {
        private const val MAX_NUM_LENGTH = 7
    }
}