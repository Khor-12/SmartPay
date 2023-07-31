package com.khor.smartpay.feature_transaction.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.feature_transaction.domain.repository.TransactionDetailRepository
import com.khor.smartpay.feature_transaction.domain.util.TransactionOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val repository: TransactionDetailRepository
) : ViewModel() {

    private val _state = mutableStateOf(TransactionDetailState())
    val state = _state

    init {
        showTransactionDetails(TransactionOrder.All)
    }

    fun onEvent(event: TransactionsEvent) {
        when (event) {
            is TransactionsEvent.ToggleOrderSection -> {
                this.state.value = this.state.value.copy(
                    isFilterListVisible = !this.state.value.isFilterListVisible
                )
            }
            is TransactionsEvent.Order -> {
                this.state.value = this.state.value.copy(
                    transactionOrder = event.transactionOrder
                )
            }
            is TransactionsEvent.OnRefresh -> {
                showTransactionDetails()
            }
        }
    }

    fun showTransactionDetails(
        transactionOrder: TransactionOrder = TransactionOrder.All
    ) {
        viewModelScope.launch {
            repository.getTransactions()
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            state.value =
                                state.value.copy(transactionDetailItems =
                                when (transactionOrder) {
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
                            state.value = state.value.copy(
                                transactionDetailItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                }.launchIn(this)
        }
    }
}