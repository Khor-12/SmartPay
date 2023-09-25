package com.khor.smartpay.feature_auth.presentation.phone_number_input

data class PhoneNumberInputState(
    var phoneNumber: String = "",
    var isEnable: Boolean = false,
    val isLoading: Boolean = false,
    val businessName: String = ""
)
