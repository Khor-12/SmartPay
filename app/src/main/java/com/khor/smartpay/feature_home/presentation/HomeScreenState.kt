package com.khor.smartpay.feature_home.presentation

import com.khor.smartpay.core.util.getCurrentMonthYear
import com.khor.smartpay.core.util.getCurrentMonthYearNum
import com.khor.smartpay.core.util.getCurrentTime

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
    val totalExpense: Double = 0.0,
    var datePickerDateTime: String = getCurrentMonthYear(),
    var numberOfMonthDays: Int = getDaysInMonth(
        getCurrentMonthYearNum().split(" ")[0].toInt(),
        getCurrentMonthYearNum().split(" ")[1].toInt()
    )
)
