package com.khor.smartpay.feature_home.domain.model

data class DepositPayload(
    val username: String = "fe1bb16c4cd6016e",
    val password: String = "aab0daa2bba09959",
    val action: String = "mmdeposit",
    val amount: Int,
    val phone: String,
    val currency: String = "UGX",
    val reference: Int,
    val reason: String  = "DEPOSITING TO SMARTPAY"
)
