package com.khor.smartpay.feature_auth.presentation.create_code.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.khor.smartpay.core.util.Screen
import com.khor.smartpay.feature_auth.presentation.verification.VerificationEvent
import com.khor.smartpay.feature_auth.presentation.verification.VerificationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCodeInput(
    modifier: Modifier,
    viewModel: VerificationViewModel,
    navController: NavController
) {
    val state = viewModel.state
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(modifier = modifier) {
        TextField(
            modifier = Modifier
                .width(150.dp)
                .focusRequester(focusRequester),
            value = state.pinCode,
            onValueChange = {
                viewModel.onEvent(VerificationEvent.OnPinCodeChange(it))
                if (it.length == 4) {
                    navController.navigate(Screen.ConfirmCodeScreen.route + "/${it}")
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