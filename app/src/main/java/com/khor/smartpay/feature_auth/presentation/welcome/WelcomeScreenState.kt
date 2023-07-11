package com.khor.smartpay.feature_auth.presentation.welcome

data class WelcomeScreenState(
    var phoneNumber: String = "",
    var isEnable: Boolean = false,
    val isLoading: Boolean = false
)
