package com.khor.smartpay.core.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.khor.smartpay.core.presentation.components.InternalScreen

data class InternalScreenState(
    var currentScreen:  MutableState<InternalScreen>? = null,
    val currentTheme: Int = 0,
    val isVerified: Boolean? = null
)