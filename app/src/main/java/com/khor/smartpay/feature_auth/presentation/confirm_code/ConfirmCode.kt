package com.khor.smartpay.feature_auth.presentation.confirm_code

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.khor.smartpay.core.data.prefdatastore.UserStore
import com.khor.smartpay.core.util.Screen
import com.khor.smartpay.feature_auth.presentation.create_code.component.CreateCodeInput
import com.khor.smartpay.feature_auth.presentation.verification.VerificationEvent
import com.khor.smartpay.feature_auth.presentation.verification.VerificationViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmCode(
    pinCode: String,
    navController: NavController
) {
    val viewModel: VerificationViewModel = hiltViewModel()
    val localContext = LocalContext.current
    val store = UserStore(localContext)

    val state = viewModel.state
    val focusRequester = remember { FocusRequester() }

    var openDialogPin by remember { mutableStateOf(false) }

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
                    viewModel.updateUserStore(userType = "seller", token = true)
                    navController.navigate(Screen.InternalScreenSeller.route)
                }

                is VerificationViewModel.UiEvent.NavigateToCreateCode -> {
                    navController.navigate(Screen.CreateCodeScreen.route)
                }

                is VerificationViewModel.UiEvent.NavigateToEnterCode -> {
                    navController.navigate(Screen.EnterCodeScreen.route)
                }
            }
        }
    }


    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    if (openDialogPin) {
        AlertDialog(
            onDismissRequest = { openDialogPin = false },
            text = { Text("Pins don't match", fontSize = 18.sp) },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialogPin = false
                    }) {
                    Text("Close")
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier,
                text = "Confirm your pin code",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(40.dp))

            Box(modifier = Modifier) {
                TextField(
                    modifier = Modifier
                        .width(150.dp)
                        .focusRequester(focusRequester),
                    value = state.pinCode,
                    onValueChange = {
                        viewModel.onEvent(VerificationEvent.OnPinCodeChange(it))
                        if (it.length == 4) {
                            if (pinCode == it) {
                                viewModel.createUserWithPin(it)
                            } else {
                                openDialogPin = true
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "----",
                            modifier = Modifier
                                .alpha(0.5f)
                                .fillMaxWidth(),
                            fontSize = 24.sp,
                            letterSpacing = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    },
                    textStyle = TextStyle.Default.copy(fontSize = 24.sp, letterSpacing = 14.sp),
                )
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
}