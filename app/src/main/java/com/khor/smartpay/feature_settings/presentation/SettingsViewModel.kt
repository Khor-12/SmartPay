package com.khor.smartpay.feature_settings.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khor.smartpay.core.data.prefdatastore.UserStore
import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_home.presentation.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(HomeScreenState())

    init {
        state.phoneNumber = authRepository.currentUser?.phoneNumber ?: ""
    }

    fun onEvent(event: SettingsEvents) {
        when (event) {
            SettingsEvents.SignOutUser -> {
                authRepository.signOut()
            }
        }
    }

    fun deleteUser(store: UserStore) {
        viewModelScope.launch {
            authRepository.performDeleteAccount()
                .onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            // Handle error, log, or display a message
                        }
                        is Resource.Loading -> {
                            // Handle loading state if needed
                        }
                        is Resource.Success -> {
                            store.clearDataStore()
                        }
                    }
                }
                .launchIn(this)
        }
    }


}