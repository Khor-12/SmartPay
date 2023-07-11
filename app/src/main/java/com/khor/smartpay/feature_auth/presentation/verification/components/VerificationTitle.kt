package com.khor.smartpay.feature_auth.presentation.verification.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun VerificationTitle(
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = "Verify your number",
        style = MaterialTheme.typography.headlineSmall
    )
}