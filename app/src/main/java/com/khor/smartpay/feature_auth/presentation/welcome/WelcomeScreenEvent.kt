package com.khor.smartpay.feature_auth.presentation.welcome

sealed class WelcomeScreenEvent() {
    data class OnPhoneNumberChange(val number: String): WelcomeScreenEvent()
}