package com.khor.smartpay.feature_auth.presentation.create_code

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.PhoneAuthProvider
import com.khor.smartpay.feature_auth.presentation.create_code.component.CreateCodeInput
import com.khor.smartpay.feature_auth.presentation.verification.VerificationEvent
import com.khor.smartpay.feature_auth.presentation.verification.VerificationViewModel
import com.khor.smartpay.feature_auth.presentation.verification.components.CodeInputField

@Composable
fun CreateCode(
    navController: NavController
) {
    val viewModel: VerificationViewModel = hiltViewModel()
    val localContext = Locale.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 60.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier,
            text = "Create a four digit pin",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(40.dp))
        CreateCodeInput(modifier = Modifier, viewModel = viewModel, navController = navController)
    }

}