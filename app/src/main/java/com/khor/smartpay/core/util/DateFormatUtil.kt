package com.khor.smartpay.core.util

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun String.toSimpleDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy - h:mm a", Locale.getDefault())

    val date = inputFormat.parse(this)
    return outputFormat.format(date!!).toString()
}

fun getCurrentTime(): String {
    val currentTime = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(currentTime)
}

fun getCurrentTimeUser(): String {
    val currentTime = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
    return dateFormat.format(currentTime)
}

fun getCurrentMonthYear(): String {
    val currentTime = Calendar.getInstance()
    val month = DateFormatSymbols.getInstance().months[currentTime.get(Calendar.MONTH)]
    val year = currentTime.get(Calendar.YEAR)
    return "$month $year"
}

fun getCurrentMonthYearNum(): String {
    val currentTime = Calendar.getInstance()
    val month = currentTime.get(Calendar.MONTH) + 1 // Month is 0-based, so add 1
    val year = currentTime.get(Calendar.YEAR)
    return "$month $year"
}