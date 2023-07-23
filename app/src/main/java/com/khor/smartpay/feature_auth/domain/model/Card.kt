package com.khor.smartpay.feature_auth.domain.model

data class Card(
    val qrCode: String = "",
    val isFrozen: Boolean = false,
    val limit: Double = 0.0,
)
