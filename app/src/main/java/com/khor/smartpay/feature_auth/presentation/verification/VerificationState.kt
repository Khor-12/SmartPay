package com.khor.smartpay.feature_auth.presentation.verification

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class VerificationState(
    val pinCode: String = "",
    var resultCode: String = "",
    var verificationStatus: Boolean = false,
    var verificationMessage: MutableState<String> = mutableStateOf(""),
    val details: String = "Start scanning to get details",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showError: Boolean = false,
    val navigateToMain: Boolean = false,
    val currentUserType: String = ""
)
