package com.khor.smartpay.feature_transaction.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.feature_transaction.domain.repository.TransactionDetailRepository
import com.khor.smartpay.feature_transaction.domain.util.TransactionOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val repository: TransactionDetailRepository
) : ViewModel() {

    init {
        showTransactionDetails(TransactionOrder.All)
    }

    private val _state = mutableStateOf(TransactionDetailState())
    val state: State<TransactionDetailState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val event = _eventFlow.asSharedFlow()

    fun onEvent(event: TransactionsEvent) {
        when (event) {
            is TransactionsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isFilterListVisible = !state.value.isFilterListVisible
                )
            }
            is TransactionsEvent.Order -> {
                _state.value = state.value.copy(
                    transactionOrder = event.transactionOrder
                )
            }
        }
    }

    fun showTransactionDetails(transactionOrder: TransactionOrder) {
        viewModelScope.launch {
            repository.getTransactions()
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value =
                                state.value.copy(transactionDetailItems = when (transactionOrder) {
                                    is TransactionOrder.All -> result.data
                                    is TransactionOrder.Buy ->
                                        result.data?.filter {
                                            it.transactionType.lowercase().startsWith("b")
                                        }

                                    is TransactionOrder.Deposit ->
                                        result.data?.filter {
                                            it.transactionType.lowercase().startsWith("d")
                                        }

                                    is TransactionOrder.Sell ->
                                        result.data?.filter {
                                            it.transactionType.lowercase().startsWith("s")
                                        }

                                    is TransactionOrder.Withdraw ->
                                        result.data?.filter {
                                            it.transactionType.lowercase().startsWith("w")
                                        }
                                } ?: emptyList(), isLoading = false)
                        }

                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                transactionDetailItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                UIEvent.ShowSnackbar(
                                    result.message ?: "Unknown Error"
                                )
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                transactionDetailItems = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }


    sealed class UIEvent() {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}