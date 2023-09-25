package com.khor.smartpay.feature_home.presentation

data class HomeScreenState(
    var phoneNumber: String = "",
    val currentBalance: Double = 0.0,
    val textFieldPhoneNumber: String = "",
    val depositAmount: String = "",
    val referenceCount: Double? = null,
    var depositErrorMsg: String = "",
    var depositError: Boolean = false,
    val phoneNumberWithCode: String = "",
    val invalidPhoneNumber: Boolean = false,
    var showProgressIndication: Boolean = false,
    val userIsSignedOut: Boolean = false,
    var isLoading: Boolean = false,
    var signingOut: Boolean = false,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0
)
