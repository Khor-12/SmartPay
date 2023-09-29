package com.khor.smartpay.core.util

import kotlinx.serialization.Serializable


@Serializable
data class AppSettings(
    val userType: String = "",
    val isUserVerified: Boolean = false
)
