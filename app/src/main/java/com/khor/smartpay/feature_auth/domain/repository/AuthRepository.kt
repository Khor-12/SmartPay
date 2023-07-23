package com.khor.smartpay.feature_auth.domain.repository

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.khor.smartpay.core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

typealias AuthStateResponse = StateFlow<Boolean>

interface AuthRepository {
    val currentUser: FirebaseUser?
    val db: FirebaseFirestore?

    suspend fun sendVerificationCode(
        number: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )

    suspend fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        activity: Activity,
        signInWithCredential: (Task<AuthResult>) -> Unit
    )

    suspend fun createUser(qrCode: String): Flow<Resource<String>>

    fun startScanning(): Flow<String?>

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
}
