package com.khor.smartpay.feature_auth.presentation.generate_qrcode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.khor.smartpay.core.util.Screen
import com.khor.smartpay.feature_auth.presentation.generate_qrcode.component.ThreeDotsLoadingAnimation
import com.khor.smartpay.feature_auth.presentation.verification.VerificationViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GenerateQrCode(navController: NavController) {
    val viewModel: VerificationViewModel = hiltViewModel()

    var openDialog by remember { mutableStateOf(false) }
    var showProgressIndicator by remember { mutableStateOf(false) }
    var openDialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is VerificationViewModel.UiEvent.ShowAlertDialog -> {
                    openDialog = event.showDialog
                    openDialogMessage = event.message
                    showProgressIndicator = false
                }

                is VerificationViewModel.UiEvent.ShowProgressIndicator -> {
                    showProgressIndicator = true
                }

                is VerificationViewModel.UiEvent.NavigateToMainScreen -> {
                    println("Navigating to main screen")
                }

                is VerificationViewModel.UiEvent.NavigateToCreateCode -> {
                    navController.navigate(Screen.CreateCodeScreen.route)
                }

                is VerificationViewModel.UiEvent.NavigateToEnterCode -> {
                    navController.navigate(Screen.EnterCodeScreen.route)
                }

                is VerificationViewModel.UiEvent.NavigateToGenerateCode -> { }
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Generating QrCode", style = MaterialTheme.typography.headlineSmall)
        ThreeDotsLoadingAnimation(
            modifier = Modifier
                .size(36.dp)
                .padding(16.dp)
        )
    }

}