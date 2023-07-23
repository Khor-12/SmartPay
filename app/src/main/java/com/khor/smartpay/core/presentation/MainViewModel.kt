package com.khor.smartpay.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    init {
        getAuthState()
        repository.currentUser
    }

    val state by mutableStateOf(InternalScreenState())

    fun getAuthState() = repository.getAuthState(viewModelScope)
}