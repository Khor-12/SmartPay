package com.khor.smartpay.feature_cards.domain.repository

import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.feature_auth.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface QrCodeRepository {

    suspend fun createCard(): Flow<Resource<Boolean>>

    suspend fun getQrCodeCards(): Flow<Resource<List<Card>>>
}