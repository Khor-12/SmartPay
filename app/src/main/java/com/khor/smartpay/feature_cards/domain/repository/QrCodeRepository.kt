package com.khor.smartpay.feature_cards.domain.repository

import com.khor.smartpay.core.util.Resource
import com.khor.smartpay.feature_auth.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface QrCodeRepository {

    suspend fun createCard(): Flow<Resource<Boolean>>

    suspend fun getQrCodeCards(): Flow<Resource<List<Card>>>

    suspend fun updateCard(
        cardId: String,
        isFrozen: Boolean?,
        limit: Double?
    ): Flow<Resource<Boolean>>

    suspend fun deleteCard(card: String): Flow<Resource<Boolean>>

}