package com.khor.smartpay.feature_payment.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khor.smartpay.R
import com.khor.smartpay.core.presentation.components.StandardToolbar
import com.khor.smartpay.feature_payment.presentation.components.PaymentAmountText
import com.khor.smartpay.feature_payment.presentation.components.PaymentButtonContainer

@Composable
fun PaymentScreen() {
    val viewModel: PaymentViewModel = hiltViewModel()
    val state = viewModel.state
//
//    LaunchedEffect(key1 = Unit) {
//        viewModel.getBalance()
//    }

    StandardToolbar(
        title = {
            Text(
                text = "Amount",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        PaymentAmountText(modifier = Modifier.padding(top = 24.dp), state)

        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Text(text = "ugx", modifier = Modifier.padding(vertical = 3.dp, horizontal = 6.dp))
        }

        PaymentButtonContainer(
            viewModel = viewModel,
            modifier = Modifier.padding(top = 32.dp),
            viewModel::onEvent
        )

    }

    if (state.transactionIsLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
            )
        }
    }

    if (state.showAlertDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(PaymentScreenEvent.ChangeAlert(false)) },
            text = {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Image(
                        colorFilter = if (state.transactionError) ColorFilter.tint(MaterialTheme.colorScheme.error)
                        else ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        painter = if (state.transactionError) painterResource(R.drawable.error_circle)
                        else painterResource(R.drawable.check_circle), contentDescription = null
                    )
                    Text(state.transactionMessage)
                }

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(PaymentScreenEvent.ChangeAlert(false))
                    }) {
                    Text("Close")
                }
            }
        )
    }
}