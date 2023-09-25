package com.khor.smartpay.feature_home.domain.repository

import com.khor.smartpay.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getTotalIncome(): Flow<Resource<String>>

    suspend fun getTotalExpense(): Flow<Resource<String>>
}