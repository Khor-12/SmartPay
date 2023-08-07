package com.khor.smartpay.feature_payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khor.smartpay.core.presentation.components.StandardToolbar
import com.khor.smartpay.feature_payment.components.PaymentAmountText
import com.khor.smartpay.feature_payment.components.PaymentButtonContainer

@Composable
fun PaymentScreen() {
    val viewModel: PaymentViewModel = hiltViewModel()
    val state = viewModel.state

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
            modifier = Modifier.padding(top = 32.dp),
            viewModel::onEvent
        )

    }



}