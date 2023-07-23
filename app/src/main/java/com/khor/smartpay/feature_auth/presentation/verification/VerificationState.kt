package com.khor.smartpay.feature_auth.presentation.verification

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class VerificationState(
    var resultCode: String = "",
    var verificationStatus: Boolean = false,
    var verificationMessage: MutableState<String> = mutableStateOf(""),
    val details: String = "Start scanning to get details"
)
