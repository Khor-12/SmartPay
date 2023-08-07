package com.khor.smartpay.feature_payment.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.khor.smartpay.feature_payment.PaymentScreenState
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PaymentAmountText(
    modifier: Modifier = Modifier,
    state: PaymentScreenState
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = if (state.amount.isNullOrBlank()) "0" else NumberFormat.getNumberInstance(Locale.US)
            .format(state.amount.toDouble()),
        style = MaterialTheme.typography.headlineLarge,
        fontSize = 48.sp,
        textAlign = TextAlign.Center,
    )
}