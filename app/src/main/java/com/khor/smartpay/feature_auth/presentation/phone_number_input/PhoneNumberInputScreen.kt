package com.khor.smartpay.feature_auth.presentation.phone_number_input

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khor.smartpay.feature_auth.presentation.phone_number_input.components.PhoneNumberInputField
import com.khor.smartpay.feature_auth.presentation.phone_number_input.components.SmartPayHeader
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ShowToast")
@Composable
fun PhoneNumberInputScreen(
    viewModel: PhoneNumberInputViewModel = hiltViewModel(),
    navigateToVerificationScreen: (String, String) -> Unit,
    userType: String
) {
    val localContext = LocalContext.current

    var openDialog by remember { mutableStateOf(false) }
    var showProgressIndicator by remember { mutableStateOf(false) }
    var openDialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PhoneNumberInputViewModel.UiEvent.ShowAlertDialog -> {
                    openDialog = event.showDialog
                    openDialogMessage = event.message
                    showProgressIndicator = false
                }

                is PhoneNumberInputViewModel.UiEvent.ShowProgressIndicator -> {
                    showProgressIndicator = true
                }
            }
        }
    }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = { Text(text = "Error") },
            text = { Text(openDialogMessage, fontSize = 18.sp) },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }) {
                    Text("Close")
                }
            }
        )
    }

    Box(modifier = Modifier) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                if (userType.lowercase() == "seller")
                    "Create a Seller Account" else
                    "Create a Parent/Student \nAccount",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 100.dp),
                textAlign = TextAlign.Center
            )
            PhoneNumberInputField(modifier = Modifier.padding(top = 40.dp), viewModel = viewModel)
            Button(
                modifier = Modifier
                    .width(280.dp)
                    .height(50.dp)
                    .offset(y = 30.dp),
                enabled = viewModel.state.isEnable,
                onClick = {
                    viewModel.sendVerificationCode(
                        number = "+256${viewModel.state.phoneNumber}",
                        localContext as Activity,
                        navigateToVerificationScreen
                    )
                }
            ) {
                Text(
                    text = "Continue",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row(modifier = Modifier.padding(top = 60.dp)) {
                Text(text = "Already have an account ")
                Text(
                    text = "Log in",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { },
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }

    if (showProgressIndicator) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(80.dp)
            )
        }
    }
}
