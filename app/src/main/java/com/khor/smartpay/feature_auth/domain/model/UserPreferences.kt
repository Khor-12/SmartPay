package com.khor.smartpay.feature_auth.domain.model

import com.khor.smartpay.feature_auth.data.local.entity.UserPreferenceEntity

data class UserPreferences(
    val userType: String = "",
    val isUserVerified: Boolean = false
) {
    fun toUserPreferenceEntity(): UserPreferenceEntity {
        return UserPreferenceEntity(
            userType = userType,
            isUserVerified = isUserVerified
        )
    }
}
