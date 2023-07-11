package com.khor.smartpay.feature_auth.presentation.verification.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ResendTextButton(
    modifier: Modifier,
    handleResend: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { handleResend() }
    ) {
        Text("Resend")
    }
}
