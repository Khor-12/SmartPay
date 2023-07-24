package com.khor.smartpay.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toSimpleDate(): String {
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
    val date = inputFormat.parse(this.toString())
    val outputFormat = SimpleDateFormat("dd MMM - h:mm a", Locale.US)

    return outputFormat.format(date!!).toString()
}
