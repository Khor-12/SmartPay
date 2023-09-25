package com.khor.smartpay.feature_auth.presentation.verification

sealed class VerificationEvent {
    data class OnCodeResultChange(val code: String): VerificationEvent()
    data class OnPinCodeChange(val pin: String): VerificationEvent()
}