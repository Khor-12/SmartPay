package com.khor.smartpay.feature_home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    authRepository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(HomeScreenState())

    init {
        state.phoneNumber = authRepository.currentUser?.phoneNumber ?: ""
    }

}