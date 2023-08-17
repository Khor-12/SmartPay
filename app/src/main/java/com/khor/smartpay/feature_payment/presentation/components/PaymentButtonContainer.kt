package com.khor.smartpay.feature_payment.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khor.smartpay.feature_payment.presentation.PaymentScreenEvent
import com.khor.smartpay.feature_payment.presentation.PaymentViewModel

@Composable
fun PaymentButtonContainer(
    viewModel: PaymentViewModel,
    modifier: Modifier,
    onEvent: (PaymentScreenEvent) -> Unit
) {
    val buttonSpacing = 8.0.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                PaymentButton(
                    value = "7",
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Number(7))
                    }
                )
                PaymentButton(
                    value = "4",
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Number(4))
                    }
                )
                PaymentButton(
                    value = "1",
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Number(1))
                    }
                )
                PaymentButton(
                    value = ".",
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Decimal)
                    }
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                PaymentButton(
                    value = "8",
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Number(8))
                    }
                )
                PaymentButton(
                    value = "5",
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Number(5))
                    }
                )
                PaymentButton(
                    value = "2",
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Number(2))
                    }
                )
                PaymentButton(
                    value = "0",
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Number(0))
                    }
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                PaymentButton(
                    value = "9",
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Number(9))
                    }
                )
                PaymentButton(
                    value = "6",
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Number(6))
                    }
                )
                PaymentButton(
                    value = "3",
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Number(3))
                    }
                )
                PaymentButton(
                    value = "00",
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.DoubleDigitZero)
                    }
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),

            ) {
                PaymentButton(
                    value = "AC",
                    modifier = Modifier
                        .size(width = 80.dp, height = 165.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Clear)
                    }
                )
                PaymentButton(
                    value = "x",
                    modifier = Modifier
                        .size(width = 80.dp, height = 165.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        onEvent(PaymentScreenEvent.Delete)
                    }
                )
            }
        }
        Button(
            onClick = {
                viewModel.makePayment()
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(60.dp)
        ) {
            Text("Confirm Payment")
        }
    }


}