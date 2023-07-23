package com.khor.smartpay.feature_auth.domain.repository

import android.app.Activity
import androidx.compose.runtime.MutableState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.khor.smartpay.core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

typealias AuthStateResponse = StateFlow<Boolean>

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun sendVerificationCode(
        number: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )

    suspend fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        activity: Activity,
        viewModelScope: CoroutineScope
    ): Flow<Resource<String>>

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
}
