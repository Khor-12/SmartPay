package com.khor.smartpay.feature_cards.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
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

    fun observeState() = snapshotFlow { state.value }

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
                    is Resource.Success -> {
                        getCards()
                    }
                }
            }.launchIn(this)
        }
    }

    fun updateCard(cardId: String, isFrozen: Boolean?, limit: Double?) {
        viewModelScope.launch {
            repository.updateCard(cardId, isFrozen, limit).onEach { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                    is Resource.Success -> state.value = state.value.copy(
                        cards = state.value.cards.map { card ->
                            if (card.qrCode == cardId && isFrozen != null) {
                                card.copy(isFrozen = isFrozen)
                            } else {
                                card
                            }
                        }
                    )
                }
            }.launchIn(this)
        }
    }

    fun deleteCard(card: String) {
        viewModelScope.launch {
            repository.deleteCard(card).onEach { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        getCards()
                    }
                }
            }.launchIn(this)
        }
    }
}