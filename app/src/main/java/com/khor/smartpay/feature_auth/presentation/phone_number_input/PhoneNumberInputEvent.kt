package com.khor.smartpay.feature_auth.presentation.phone_number_input

sealed class PhoneNumberInputEvent() {
    data class OnPhoneNumberChange(val number: String): PhoneNumberInputEvent()
    data class OnBusinessNameChange(val name: String): PhoneNumberInputEvent()
}