package com.khor.smartpay.feature_home.domain.repository

import com.khor.smartpay.feature_home.domain.model.DepositPayload
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface EasyPayApi {
    @POST
    fun makeDeposit(@Url url: String, @Body payload: DepositPayload): Call<Map<String, Any>>
}
