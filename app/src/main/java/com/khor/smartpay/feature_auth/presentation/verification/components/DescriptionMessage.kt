package com.khor.smartpay.feature_auth.presentation.verification.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

@Composable
fun DescriptionMessage(
    modifier: Modifier,
    phoneNumber: String
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            append("Enter the verification code we sent to\n")
            append(" ")
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(phoneNumber)
            }
        },
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )

}