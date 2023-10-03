package com.khor.smartpay.feature_home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_home.domain.model.DepositPayload
import com.khor.smartpay.feature_home.domain.repository.EasyPayApi
import com.khor.smartpay.feature_payment.domain.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository, private val paymentRepository: PaymentRepository
) : ViewModel() {
    var state by mutableStateOf(HomeScreenState())

    init {
        state.phoneNumber = authRepository.currentUser?.phoneNumber ?: ""
        getCurrentBalance()
        getTotalIncome()
        getTotalExpense()
    }

    fun clearInputFields() {
        state = state.copy(
            textFieldPhoneNumber = "", depositAmount = "",
            depositError = false, depositErrorMsg = "",
            isLoading = false
        )
    }


    fun getReferenceCount() {
        viewModelScope.launch {
            authRepository.getCurrentReference()
                .onEach { result ->
                    when (result) {
                        is Resource.Error -> Unit
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            state = state.copy(
                                referenceCount = result.data
                            )
                        }
                    }
                }.launchIn(this)
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun makeDeposit(easyPayApi: EasyPayApi, payload: DepositPayload) {
        easyPayApi.makeDeposit("https://www.easypay.co.ug/api/", payload)
            .enqueue(object : Callback<Map<String, Any>> {
                override fun onResponse(
                    call: Call<Map<String, Any>>,
                    response: Response<Map<String, Any>>
                ) {
                    if (response.isSuccessful) {
                        val responseBodyMap = response.body()
                        println(responseBodyMap)
                        // Handle the map data here
                        if (responseBodyMap != null) {
                            // Access specific values in the map
                            val status = responseBodyMap["success"]
                            state.depositErrorMsg = "$status"
                            if (status != 0.0) {
                                viewModelScope.launch {
                                    authRepository.makeDeposit(
                                        payload.amount.toDouble(),
                                        payload.phone
                                    ).onEach { result ->
                                        when (result) {
                                            is Resource.Error -> {
                                                println("This is the error msg: ${result.message}")
                                            }
                                            is Resource.Loading -> Unit
                                            is Resource.Success -> {
                                                getCurrentBalance()
                                                state = state.copy(
                                                    isLoading = false,
                                                    depositErrorMsg = "The transaction was successful"
                                                )
                                            }
                                        }

                                    }.launchIn(this)
                                }
                            }
                        }
                    } else {
                        // Handle the error response here
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    // Handle the network error here
                    state = state.copy(
                        isLoading = false,
                        depositError = true,
                        depositErrorMsg = "check your internet connection"
                    )
                }
            })
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnPhoneNumberChange -> {
                val length = event.number.length
                if (length != 13) {
                    state = state.copy(
                        textFieldPhoneNumber = event.number
                    )
                }
            }

            is HomeScreenEvent.OnDepositAmountChange -> {
                val length = event.number.length
                if (length != 10) {
                    state = state.copy(
                        depositAmount = event.number
                    )
                }
            }
        }
    }

    fun getCurrentBalance() {
        viewModelScope.launch {
            paymentRepository.getTotalBalance(authRepository.currentUser!!.uid).collectLatest {
                state = state.copy(
                    currentBalance = it
                )
            }
        }
    }

    fun getTotalIncome() {
        viewModelScope.launch {
            paymentRepository.getTotalIncome().collectLatest {
                state = state.copy(
                    totalIncome = it
                )
            }
        }
    }

    fun getTotalExpense() {
        viewModelScope.launch {
            paymentRepository.getTotalExpense().collectLatest {
                state = state.copy(
                    totalExpense = it
                )
            }
        }
    }
}