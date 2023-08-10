package com.khor.smartpay.feature_home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.khor.smartpay.R
import com.khor.smartpay.core.presentation.components.StandardToolbar
import com.khor.smartpay.feature_home.components.PaymentButton

@Composable
fun HomeScreen() {
    StandardToolbar(
        title = { Text("+256787102643", style = MaterialTheme.typography.titleMedium) },
        navActions = {
            IconButton(
                onClick = {

                }, modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.settings),
                    modifier = Modifier.size(28.dp),
                    contentDescription = "Settings"
                )
            }
        })

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                    append("15,000")
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
                onClick = { },
                icon = painterResource(R.drawable.down_arrow),
                title = "DEPOSIT"
            )
            PaymentButton(
                modifier = Modifier.weight(1f),
                onClick = { },
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
                        "2023",
                        modifier = Modifier.padding(top = 3.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    IconButton(onClick = {}, modifier = Modifier.offset(y = (-10).dp)) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "pick year"
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                App()
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("|", color = Color(0xFF4CAF50), fontSize = 26.sp)
                        Text(
                            "32,000 Ush",
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("|", color = MaterialTheme.colorScheme.error, fontSize = 26.sp)
                        Text(
                            "12,000 Ush",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

    }
}


data class MonthlyData(val month: String, val income: Float, val expense: Float)

@Composable
fun MonthlyHistogram(monthlyData: List<MonthlyData>) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        monthlyData.forEach { data ->
            MonthlyHistogramItem(data)
        }
    }

}

@Composable
fun MonthlyHistogramItem(data: MonthlyData) {
    val maxAmount = maxOf(data.income, data.expense)
    val incomeRatio = data.income / maxAmount
    val expenseRatio = data.expense / maxAmount

    Box(
        modifier = Modifier
            .width(40.dp)
            .padding(horizontal = 4.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(incomeRatio)
                .width(25.dp)
                .background(Color(0xFF4CAF50))
        )
        Box(
            modifier = Modifier
                .fillMaxHeight(expenseRatio)
                .width(25.dp)
                .background(MaterialTheme.colorScheme.error)
        )
        Box(
            modifier = Modifier
                .width(25.dp)
                .offset(y = (165).dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = data.month,
                fontSize = 12.sp,
                modifier = Modifier.padding(vertical = 4.dp),
                textAlign = TextAlign.Center
            )
        }

    }
}


@Composable
fun App() {
    val monthlyData = listOf(
        MonthlyData("Jan", 1200f, 800f),
        MonthlyData("Feb", 1500f, 1000f),
        MonthlyData("Mar", 1800f, 1200f),
        MonthlyData("Apr", 1600f, 1100f),
        MonthlyData("May", 2000f, 1300f),
        MonthlyData("Jun", 2100f, 1400f),
        MonthlyData("Jul", 2300f, 1500f),
        MonthlyData("Aug", 2200f, 1600f),
        MonthlyData("Sep", 2500f, 1700f),
        MonthlyData("Oct", 2400f, 1800f),
        MonthlyData("Nov", 2600f, 1900f),
        MonthlyData("Dec", 2800f, 2000f)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MonthlyHistogram(monthlyData)
    }
}
