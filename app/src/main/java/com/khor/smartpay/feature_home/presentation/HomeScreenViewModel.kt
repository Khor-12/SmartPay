package com.khor.smartpay.feature_home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_payment.domain.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val paymentRepository: PaymentRepository
) : ViewModel() {
    var state by mutableStateOf(HomeScreenState())

    init {
        state.phoneNumber = authRepository.currentUser?.phoneNumber ?: ""
        getCurrentBalance()
    }

    fun getCurrentBalance() {
        viewModelScope.launch {
            paymentRepository.getTotalBalance(authRepository.currentUser!!.uid)
                .collectLatest {
                    state = state.copy(
                        currentBalance = it
                    )
                }
        }
    }
}