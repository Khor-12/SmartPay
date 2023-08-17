package com.khor.smartpay.di

import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_cards.data.repository.QrCodeRepositoryImpl
import com.khor.smartpay.feature_cards.domain.repository.QrCodeRepository
import com.khor.smartpay.feature_payment.data.repository.PaymentRepositoryImpl
import com.khor.smartpay.feature_payment.domain.repository.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentModule {

    @Provides
    @Singleton
    fun providePaymentRepository(
        authRepository: AuthRepository
    ): PaymentRepository = PaymentRepositoryImpl(authRepository)
}