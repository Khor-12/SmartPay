package com.khor.smartpay.feature_auth.domain.model


data class SmartUser(
    val qrCodes: List<String> = emptyList(),
    val userType: String
)
