package com.khor.smartpay.feature_auth.presentation.verification

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.core.util.Screen
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(VerificationState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: VerificationEvent) {
        when (event) {
            is VerificationEvent.OnCodeResultChange -> {
                if (event.code.length != 7)
                    state = state.copy(
                        resultCode = event.code
                    )
            }

            is VerificationEvent.OnPinCodeChange -> {
                if (event.pin.length != 5)
                    state = state.copy(
                        pinCode = event.pin
                    )
            }
        }
    }

    fun createUserWithPin(pinCode: String) {
        viewModelScope.launch {
            repository.createUserWithCode(pinCode)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> _eventFlow.emit(UiEvent.ShowAlertDialog(result.message.toString()))
                        is Resource.Loading -> _eventFlow.emit(UiEvent.ShowProgressIndicator(true))
                        is Resource.Success -> {
                            _eventFlow.emit(UiEvent.NavigateToMainScreen)
                        }
                    }
                }
        }
    }

    private fun navigateToCodeInput() {
        viewModelScope.launch {
            repository.checkUserExistenceInDb()
                .onEach { result ->
                    when (result) {
                        is Resource.Error -> Unit
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            if (result.data == true) {
                                _eventFlow.emit(UiEvent.NavigateToEnterCode)
                            } else if (result.data == false) {
                                _eventFlow.emit(UiEvent.NavigateToCreateCode)
                            }
                        }
                    }

                }.launchIn(this)
        }
    }

    private fun startScanning() {
        viewModelScope.launch {
            repository.startScanning().collect {
                if (!it.isNullOrBlank()) {
                    createUser(it)
                }
            }
        }
    }

    fun loginUser(pinCode: String) {
        viewModelScope.launch {
            repository.loginUserWithCode(pinCode)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> _eventFlow.emit(UiEvent.ShowAlertDialog(result.message.toString()))
                        is Resource.Loading -> _eventFlow.emit(UiEvent.ShowProgressIndicator(true))
                        is Resource.Success -> {
                            _eventFlow.emit(UiEvent.NavigateToMainScreen)
                        }
                    }
                }
        }
    }

    private fun createUser(qrCode: String) {
        viewModelScope.launch {
            repository.createUser(qrCode).collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Error -> _eventFlow.emit(UiEvent.ShowAlertDialog(result.message.toString()))
                    is Resource.Success -> {
                        _eventFlow.emit(UiEvent.NavigateToMainScreen)
                    }
                }
            }
        }
    }

    fun resendVerificationCode(
        number: String,
        activity: Activity
    ) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.ShowProgressIndicator(true))
            repository.sendVerificationCode(
                number = number,
                activity = activity,
                callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {}

                    override fun onVerificationFailed(p0: FirebaseException) {
                        viewModelScope.launch {
                            _eventFlow.emit(UiEvent.ShowAlertDialog(p0.message ?: "", true))
                        }
                    }

                    override fun onCodeSent(
                        verificationId: String, p1: PhoneAuthProvider.ForceResendingToken
                    ) {
                        super.onCodeSent(verificationId, p1)
                    }
                }
            )
        }

    }

    fun signInWithCredentials(
        phoneAuthCredential: PhoneAuthCredential,
        activity: Activity,
        userType: String
    ) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.ShowProgressIndicator(true))
            repository.signInWithPhoneAuthCredential(
                phoneAuthCredential,
                activity
            ) { task ->
                if (task.isSuccessful) {
                    if (userType.lowercase() == "parent") {
                        startScanning()
                    } else if (userType.lowercase() == "seller") {
                        navigateToCodeInput()
                    } else if (userType.lowercase() == "login") {
                        launch {
                            repository.checkUserType().collect { result ->
                                when (result) {
                                    is Resource.Error -> _eventFlow.emit(UiEvent.ShowAlertDialog(result.message.toString()))
                                    is Resource.Loading -> _eventFlow.emit(UiEvent.ShowProgressIndicator(true))
                                    is Resource.Success -> {
                                        println(result.data)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        viewModelScope.launch {
                            _eventFlow.emit(
                                UiEvent.ShowAlertDialog(
                                    message = "Verification failed.." + (task.exception as FirebaseAuthInvalidCredentialsException).message,
                                    showDialog = true
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowAlertDialog(val message: String, val showDialog: Boolean = true) : UiEvent()
        data class ShowProgressIndicator(val show: Boolean) : UiEvent()
        object NavigateToMainScreen : UiEvent()
        object NavigateToCreateCode : UiEvent()
        object NavigateToEnterCode : UiEvent()
    }
}