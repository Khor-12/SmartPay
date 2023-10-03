package com.khor.smartpay.feature_home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.khor.smartpay.R
import com.khor.smartpay.core.presentation.components.StandardToolbar
import com.khor.smartpay.core.util.Screen
import com.khor.smartpay.feature_home.domain.model.DepositPayload
import com.khor.smartpay.feature_home.domain.repository.EasyPayApi
import com.khor.smartpay.feature_home.presentation.components.PaymentButton
import com.khor.smartpay.feature_home.presentation.components.TransactionAlertDialog
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeScreenSeller(
    navController: NavController,
    easyPayApi: EasyPayApi
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state = viewModel.state

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("2023") }

    val showDeposit = remember {
        mutableStateOf(false)
    }
    val showWithdraw = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getCurrentBalance()
    }

    StandardToolbar(
        title = { Text(state.phoneNumber, style = MaterialTheme.typography.titleMedium) },
        navActions = {
            IconButton(
                onClick = {
                    navController.navigate(Screen.SettingsScreen.route)
                }, modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.settings),
                    modifier = Modifier.size(28.dp),
                    contentDescription = "Settings"
                )
            }
        }
    )



    if (showDeposit.value) {
        TransactionAlertDialog(
            transactionType = "DEPOSIT",
            onClick = {
                state.isLoading = true
                viewModel.getReferenceCount()
                if (state.referenceCount != null) {
                    val payload = DepositPayload(
                        amount = viewModel.state.depositAmount.toInt(),
                        phone = "256${viewModel.state.textFieldPhoneNumber.substring(1)}",
                        reference = state.referenceCount.toInt()
                    )
                    viewModel.makeDeposit(easyPayApi, payload)
                }
            },
            errorMessage = state.depositErrorMsg,
            showDeposit,
            viewModel
        )
    }

    if (showWithdraw.value) {
        TransactionAlertDialog(
            transactionType = "WITHDRAW",
            onClick = {

            },
            errorMessage = "",
            showWithdraw,
            viewModel
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .alpha(0.7f)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp),
                text = "Total Balance",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 12.dp),
                text = buildAnnotatedString {
                    append(
                        NumberFormat.getNumberInstance(Locale.US).format(
                            state.currentBalance.toDouble()
                        )
                    )
                    withStyle(
                        SpanStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append("Ush")
                    }
                },
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PaymentButton(
                modifier = Modifier.weight(1f),
                onClick = { showWithdraw.value = true },
                icon = painterResource(R.drawable.up_arrow),
                title = "WITHDRAW",
                modifierText = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Analytics",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Balance",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Medium
                )
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        selectedOption,
                        modifier = Modifier.padding(top = 3.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Column {
                        IconButton(
                            onClick = { expanded = !expanded },
                            modifier = Modifier.offset(y = (-10).dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "pick year"
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            // Create DropdownMenuItems
                            DropdownMenuItem(
                                onClick = {
                                    selectedOption = "2023"
                                    expanded = false
                                }
                            ) {
                                Text(text = "2023")
                            }
                        }
                    }

                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), horizontalArrangement = Arrangement.spacedBy(80.dp)
            ) {
                Column {
                    Text(
                        "Income",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .alpha(0.8f)
                            .padding(start = 10.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("|", color = Color(0xFF4CAF50), fontSize = 26.sp)
                        Text(
                            "${state.totalIncome} Ush",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Column {
                    Text(
                        "Expense",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .alpha(0.8f)
                            .padding(start = 10.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("|", color = MaterialTheme.colorScheme.error, fontSize = 26.sp)
                        Text(
                            "-${state.totalExpense} Ush",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

    }
}