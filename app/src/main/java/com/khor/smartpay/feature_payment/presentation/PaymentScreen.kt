package com.khor.smartpay.feature_payment.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.items
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khor.smartpay.R
import com.khor.smartpay.core.presentation.components.StandardToolbar
import com.khor.smartpay.feature_payment.presentation.components.PaymentAmountText
import com.khor.smartpay.feature_payment.presentation.components.PaymentButton
import com.khor.smartpay.feature_payment.presentation.components.PaymentButtonContainer

import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button

@OptIn(ExperimentalFoundationApi::class)
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

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            PaymentAmountText(modifier = Modifier.padding(top = 18.dp), state)

            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(text = "ugx", modifier = Modifier.padding(vertical = 3.dp, horizontal = 6.dp))
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PaymentButtonContainer(
                        actions = paymentActions,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(3.5f),
                        viewModel::onEvent,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f), verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        PaymentButton(
                            value = "AC", modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) {

                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        PaymentButton(
                            value = "x", modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) {

                        }
                    }
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp), contentAlignment = Alignment.TopStart) {
                    Button(onClick = {  }, modifier = Modifier
                        .fillMaxWidth()
                        ) {
                        Text(text = "Confirm Payment", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
        if (state.transactionIsLoading) {
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


    if (state.showAlertDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(PaymentScreenEvent.ChangeAlert(false)) },
            text = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        colorFilter = if (state.transactionError) ColorFilter.tint(MaterialTheme.colorScheme.error)
                        else ColorFilter.tint(Color(0xFF4CAF50)),
                        painter = if (state.transactionError) painterResource(R.drawable.error_circle)
                        else painterResource(R.drawable.check_circle), contentDescription = null
                    )
                    Text(state.transactionMessage)
                }

            },
            confirmButton = {
            }
        )
    }


}