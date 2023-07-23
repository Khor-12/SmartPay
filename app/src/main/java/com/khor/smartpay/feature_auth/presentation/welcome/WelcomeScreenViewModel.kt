package com.khor.smartpay.feature_auth.presentation.welcome

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.khor.smartpay.core.util.Screen
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(WelcomeScreenState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: WelcomeScreenEvent) {
        when (event) {
            is WelcomeScreenEvent.OnPhoneNumberChange -> {
                val length = event.number.length
                if (length != 10) {
                    state = state.copy(
                        phoneNumber = event.number
                    )
                }
                state.isEnable = state.phoneNumber.length == 9
            }
        }
    }


    fun sendVerificationCode(
        number: String,
        activity: Activity,
        navigateToVerificationScreen: (String, String) -> Unit
    ) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.ShowProgressIndicator(true))
            repository.sendVerificationCode(
                number = number,
                activity = activity,
                callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        viewModelScope.launch {
                            _eventFlow.emit(UiEvent.ShowAlertDialog(p0.message ?: "", true))
                        }

                    }
                    override fun onCodeSent(
                        verificationId: String, p1: PhoneAuthProvider.ForceResendingToken
                    ) {
                        super.onCodeSent(verificationId, p1)
                        navigateToVerificationScreen(verificationId, number)
                    }
                }
            )
        }

    }

    sealed class UiEvent {
        data class ShowAlertDialog(val message: String, val showDialog: Boolean): UiEvent()
        data class ShowProgressIndicator(val show: Boolean): UiEvent()
    }

}