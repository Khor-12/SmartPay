package com.khor.smartpay.feature_home.presentation

sealed class HomeScreenEvent() {
    data class OnPhoneNumberChange(val number: String): HomeScreenEvent()
    data class OnDepositAmountChange(val number: String): HomeScreenEvent()
}