package com.khor.smartpay.feature_auth.presentation.welcome.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khor.smartpay.feature_auth.presentation.welcome.WelcomeScreenEvent
import com.khor.smartpay.feature_auth.presentation.welcome.WelcomeScreenViewModel

@SuppressLint("UnrememberedMutableState", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberInputField(
    modifier: Modifier,
    viewModel: WelcomeScreenViewModel
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = "Enter Your Phone Number",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = viewModel.state.phoneNumber,
            onValueChange = {
                viewModel.onEvent(WelcomeScreenEvent.OnPhoneNumberChange(it))
            },
            placeholder = {
                Text(
                    text = "7XXXXXXXX",
                    modifier = Modifier.alpha(0.5f),
                    fontSize = 24.sp
                )
            },
            textStyle = TextStyle.Default.copy(fontSize = 24.sp),
            leadingIcon = {
                Text(
                    text = "+256",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 18.dp, end = 8.dp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
        )
    }


}