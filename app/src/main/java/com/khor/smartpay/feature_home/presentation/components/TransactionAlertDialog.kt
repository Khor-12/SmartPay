package com.khor.smartpay.feature_home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.khor.smartpay.feature_home.presentation.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionAlertDialog(
    transactionType: String,
    onClick: () -> Unit,
    errorMessage: String,
    show: MutableState<Boolean>
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(transactionType) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("MTN, Airtel and Africell")
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Amount (1,000 - 5,000,000)", modifier = Modifier.alpha(0.7f)) }
                )
                Button(onClick = { onClick() }, modifier = Modifier.fillMaxWidth()) {
                    Text(transactionType, modifier = Modifier.padding(5.dp))
                }
                Text(text = errorMessage)
            }
        },
        confirmButton = {
            TextButton(onClick = {
               show.value = false
            }) {
                Text(text = "close")
            }
        }
    )
}