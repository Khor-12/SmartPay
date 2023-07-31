package com.khor.smartpay.di

import android.app.Application
import androidx.room.Room
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_transaction.data.local.TransactionDetailDatabase
import com.khor.smartpay.feature_transaction.data.repository.TransactionDetailRepositoryImpl
import com.khor.smartpay.feature_transaction.domain.repository.TransactionDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TransactionDetailModule {

    @Provides
    @Singleton
    fun providesTransactionDetail(
        db: TransactionDetailDatabase,
        authRepository: AuthRepository
    ): TransactionDetailRepository {
        return TransactionDetailRepositoryImpl(db.dao, authRepository)
    }

    @Provides
    @Singleton
    fun provideTransactionDetailDatabase(app: Application): TransactionDetailDatabase {
        return Room.databaseBuilder(
            app, TransactionDetailDatabase::class.java, "local_transaction_db"
        ).build()
    }
}