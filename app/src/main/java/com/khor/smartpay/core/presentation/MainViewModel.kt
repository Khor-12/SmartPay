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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        /*        val userStore = UserStore(LocalContext.current)
                getAuthState(store = userStore)*/
        repository.currentUser
        viewModelScope.launch {
            delay(100)
            _isLoading.value = false
        }
    }


    var state by mutableStateOf(InternalScreenState())

    fun getAuthState(store: UserStore) = repository.getAuthState(viewModelScope, store)

    fun getColorScheme(store: UserStore) {
        viewModelScope.launch {
            store.getColorScheme.collect {
                state = state.copy(
                    currentTheme = it
                )
            }
        }
    }
}