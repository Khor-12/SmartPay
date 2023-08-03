package com.khor.smartpay.feature_cards.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_cards.domain.repository.QrCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val repository: QrCodeRepository,
    private val authRepository: AuthRepository
) : ViewModel() {


    val state = mutableStateOf(CardsScreenState())

    init {
        getCards()
    }

    fun getCards() {
        viewModelScope.launch {
            repository.getQrCodeCards().onEach { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            cards = result.data ?: emptyList()
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun startScan() {
        viewModelScope.launch {
            repository.createCard().onEach { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                    is Resource.Success -> Unit
                }
            }.launchIn(this)
        }
    }
}