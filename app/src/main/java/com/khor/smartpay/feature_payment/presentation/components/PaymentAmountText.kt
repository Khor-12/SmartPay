package com.khor.smartpay.feature_payment.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.khor.smartpay.feature_payment.presentation.PaymentScreenState
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PaymentAmountText(
    modifier: Modifier = Modifier,
    state: PaymentScreenState
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = if (state.amount.isNullOrBlank()) "0.0" else NumberFormat.getNumberInstance(Locale.US).apply {
            maximumFractionDigits = 3 // Set the maximum number of decimal places
            minimumFractionDigits = 2 // Set the minimum number of decimal places (to show trailing zeros)
        }.format(state.amount.toDouble()),
        style = MaterialTheme.typography.headlineLarge,
        fontSize = 48.sp,
        textAlign = TextAlign.Center,
    )
}