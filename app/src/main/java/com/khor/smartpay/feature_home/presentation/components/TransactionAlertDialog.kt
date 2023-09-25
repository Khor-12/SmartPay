package com.khor.smartpay.feature_home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.khor.smartpay.feature_home.presentation.HomeScreenEvent
import com.khor.smartpay.feature_home.presentation.HomeScreenViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionAlertDialog(
    transactionType: String,
    onClick: () -> Unit,
    errorMessage: String,
    show: MutableState<Boolean>,
    viewModel: HomeScreenViewModel
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(transactionType) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("MTN and Airtel")
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = viewModel.state.textFieldPhoneNumber,
                    onValueChange = {
                        viewModel.onEvent(HomeScreenEvent.OnPhoneNumberChange(it))
                    },
                    textStyle = MaterialTheme.typography.titleLarge,
                    placeholder = { Text("Enter Number", modifier = Modifier.alpha(0.7f)) }
                )
                OutlinedTextField(
                    textStyle = MaterialTheme.typography.titleLarge,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = viewModel.state.depositAmount,
                    onValueChange = { viewModel.onEvent(HomeScreenEvent.OnDepositAmountChange(it)) },
                    placeholder = {
                        Text(
                            "Amount (1,000 - 5,000,000)",
                            modifier = Modifier.alpha(0.7f)
                        )
                    }
                )
                Button(onClick = { onClick() }, modifier = Modifier.fillMaxWidth()) {
                    Text(transactionType, modifier = Modifier.padding(5.dp))
                }
                if (viewModel.state.isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth().height(40.dp).clip(
                        CircleShape))
                } else {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = errorMessage,
                        textAlign = TextAlign.Center,
                        color = if (viewModel.state.depositError) MaterialTheme.colorScheme.error else Color(
                            0xFF4CAF50
                        )
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                show.value = false
                viewModel.clearInputFields()
            }) {
                Text(text = "close")
            }
        }
    )
}
