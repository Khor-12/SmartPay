package com.khor.smartpay.feature_auth.presentation.welcome

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khor.smartpay.feature_auth.presentation.welcome.components.PhoneNumberInputField
import com.khor.smartpay.feature_auth.presentation.welcome.components.SmartPayHeader
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("ShowToast")
@Composable
fun WelcomeScreen(
    viewModel: WelcomeScreenViewModel = hiltViewModel(),
    navigateToVerificationScreen: (String, String) -> Unit
) {
    val localContext = LocalContext.current

    var openDialog by  remember { mutableStateOf(false) }
    var showProgressIndicator by  remember { mutableStateOf(false) }
    var openDialogMessage by  remember { mutableStateOf("") }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is WelcomeScreenViewModel.UiEvent.ShowAlertDialog -> {
                    openDialog = event.showDialog
                    openDialogMessage = event.message
                    showProgressIndicator = false
                }
                is WelcomeScreenViewModel.UiEvent.ShowProgressIndicator -> {
                    showProgressIndicator = true
                }
            }
        }
    }

    if (showProgressIndicator) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 200.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier
                .height(60.dp)
                .width(60.dp))
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        SmartPayHeader(
            modifier = Modifier.padding(top = 100.dp),
            subTitle = "Need Cashless Payment"
        )
        PhoneNumberInputField(modifier = Modifier.padding(top = 60.dp), viewModel = viewModel)
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
        Text(
            text = "v 1.0.0",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .alpha(0.5f)
                .padding(top = 140.dp)
        )
    }
}
