package com.khor.smartpay

import android.app.Application
import com.khor.smartpay.feature_home.domain.repository.EasyPayApi
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class SmartPayApplication : Application() {
    lateinit var easyPayApi: EasyPayApi

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.easypay.co.ug/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        easyPayApi = retrofit.create(EasyPayApi::class.java)
    }
}
