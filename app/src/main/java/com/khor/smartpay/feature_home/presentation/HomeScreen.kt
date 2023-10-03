package com.khor.smartpay.feature_home.presentation

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.compose.rememberNavController
import com.khor.smartpay.R
import com.khor.smartpay.SmartPayApplication
import com.khor.smartpay.core.data.prefdatastore.UserStore
import com.khor.smartpay.core.presentation.components.StandardToolbar
import com.khor.smartpay.core.util.Screen
import com.khor.smartpay.feature_home.domain.model.DepositPayload
import com.khor.smartpay.feature_home.domain.repository.EasyPayApi
import com.khor.smartpay.feature_home.presentation.components.Graph
import com.khor.smartpay.feature_home.presentation.components.PaymentButton
import com.khor.smartpay.feature_home.presentation.components.TransactionAlertDialog
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random

@Composable
fun HomeScreen(
    navController: NavController,
    easyPayApi: EasyPayApi
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state = viewModel.state

    val expanded = remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("2023") }
    // Declaring a string value to store date in string format
    val mDate = remember { mutableStateOf("") }

    val showDeposit = remember {
        mutableStateOf(false)
    }
    val showWithdraw = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val store = UserStore(context)
    val showDialog = remember { mutableStateOf(false) }

    val yStep = 50
    val random = Random()
    /* to test with random points */
    val points = (0..9).map {
        var num = random.nextInt(350)
        if (num <= 50)
            num += 100
        num.toFloat()
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

    if (showDialog.value) {
        MonthYearPickerDialog(
            onDateSelected = {
                val date = it.split(" ")
                viewModel.state.numberOfMonthDays = getDaysInMonth(date[1].toInt(), date[0].toInt())
                viewModel.state.datePickerDateTime = formatDate(date[0].toInt(), date[1].toInt())
                showDialog.value = false
            },
            onDismissRequest = {
                showDialog.value = false
            }
        )
    }

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
                onClick = {
                    showDeposit.value = true
                },
                icon = painterResource(R.drawable.down_arrow),
                title = "DEPOSIT"
            )
            PaymentButton(
                modifier = Modifier.weight(1f),
                onClick = { showWithdraw.value = true },
                icon = painterResource(R.drawable.up_arrow),
                title = "WITHDRAW"
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
                    .padding(top = 16.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Balance",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                )
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Text(
                        state.datePickerDateTime,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Column {
                        IconButton(
                            onClick = { showDialog.value = true },
                            modifier = Modifier.offset(y = (-13).dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "pick year"
                            )
                        }
                    }

                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .height(230.dp)
            ) {
                Graph(
                    modifier = Modifier
                        .fillMaxSize().padding(end = 8.dp),
                    xValues = (0 until state.numberOfMonthDays).map { it + 1 },
                    yValues = (0..6).map { (it + 1) * yStep },
                    points = points,
                    paddingSpace = 20.dp,
                    verticalStep = yStep
                )
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Expense",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .alpha(0.8f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "${state.totalExpense} Ush",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}


@Composable
fun MonthYearPickerDialog(
    onDateSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)

    val mDatePickerDialog = remember {
        MonthYearPickerDialogImpl(
            mContext,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, _ ->
                val formattedDate = "${monthOfYear + 1} $year"
                onDateSelected(formattedDate)
            },
            mYear,
            mMonth
        ).apply {
            setOnDismissListener {
                onDismissRequest()
            }
        }
    }

    DisposableEffect(Unit) {
        mDatePickerDialog.show()

        onDispose {
            mDatePickerDialog.dismiss()
        }
    }
}

class MonthYearPickerDialogImpl(
    context: Context,
    onDateSetListener: DatePickerDialog.OnDateSetListener?,
    year: Int,
    monthOfYear: Int
) : DatePickerDialog(context, onDateSetListener, year, monthOfYear, 1) {
    override fun onDateChanged(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        super.onDateChanged(view, year, month, 1)
    }
}


private fun formatDate(monthOfYear: Int, year: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.MONTH, monthOfYear)
    calendar.set(Calendar.YEAR, year)

    val dateFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

fun getDaysInMonth(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month - 1) // Month is 0-based in Calendar

    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}
