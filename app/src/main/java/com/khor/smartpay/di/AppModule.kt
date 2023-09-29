package com.khor.smartpay.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.khor.smartpay.feature_auth.data.local.entity.UserPreferenceDatabase
import com.khor.smartpay.feature_auth.data.repository.AuthRepositoryImpl
import com.khor.smartpay.feature_auth.domain.repository.AuthRepository
import com.khor.smartpay.feature_transaction.data.local.TransactionDetailDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAuthRepository(
        scanner: GmsBarcodeScanner,
        db: UserPreferenceDatabase
    ): AuthRepository = AuthRepositoryImpl(
        auth = Firebase.auth,
        scanner = scanner,
        firestore = Firebase.firestore,
        dao = db.dao
    )

    @Provides
    @Singleton
    fun providePreferenceDatabase(app: Application): UserPreferenceDatabase {
        return Room.databaseBuilder(
            app, UserPreferenceDatabase::class.java, "local_preference_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideBarCodeOptions(): GmsBarcodeScannerOptions {
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    }

    @Singleton
    @Provides
    fun provideBarCodeScanner(
        context: Context,
        options: GmsBarcodeScannerOptions
    ): GmsBarcodeScanner {
        return GmsBarcodeScanning.getClient(context, options)
    }

}
