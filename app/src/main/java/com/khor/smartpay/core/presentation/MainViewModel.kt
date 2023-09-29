package com.khor.smartpay.core.presentation

import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khor.smartpay.core.data.prefdatastore.UserStore
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {
    val isLoading = MutableStateFlow(true)

    var state by mutableStateOf(InternalScreenState())

    init {
        getUserType()
        repository.currentUser
        viewModelScope.launch {
            delay(2000)
            isLoading.value = false
        }
    }

    fun getAuthState() = repository.getAuthState(viewModelScope)

    private fun getUserType() {
        viewModelScope.launch {
            repository.getUserType().collect {
                state = state.copy(
                    userType = it ?: ""
                )
            }
        }
    }
}