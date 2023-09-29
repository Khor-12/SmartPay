package com.khor.smartpay.feature_auth.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserPreferenceEntity(
    val userType: String = "",
    val isUserVerified: Boolean = false,
    @PrimaryKey val id: Int? = null
)
