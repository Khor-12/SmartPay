package com.khor.smartpay.feature_payment.domain.repository

import com.khor.smartpay.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {

    suspend fun makePayment(amount: Double): Flow<Resource<String>>

    suspend fun getTotalBalance(userUid: String): Flow<Double>
}