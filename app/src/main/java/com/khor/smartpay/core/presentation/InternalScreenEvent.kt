package com.khor.smartpay.core.presentation

import androidx.compose.runtime.MutableState
import com.khor.smartpay.core.presentation.components.InternalScreen

sealed class InternalScreenEvent {
    data class OnNavigate(val screen: MutableState<InternalScreen>): InternalScreenEvent()
}