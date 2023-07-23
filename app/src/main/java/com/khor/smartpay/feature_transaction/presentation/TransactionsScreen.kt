package com.khor.smartpay.feature_transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.khor.smartpay.R
import com.khor.smartpay.core.presentation.components.StandardToolbar
import com.khor.smartpay.feature_transaction.component.TransactionCard

@Composable
fun TransactionsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolbar(
            title = { Text("Transactions") },
            navActions = {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "sort by"
                    )
                }
            }
        )
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                TransactionCard(
                    transactionLetter = "W",
                    phoneNumber = "0787106856",
                    transactionDateTime = "2 Aug 8:00pm",
                    amount = "-8,000"
                )
            }
            item {
                TransactionCard(
                    transactionLetter = "D",
                    phoneNumber = "07323344234",
                    transactionDateTime = "10 Oct 2:00am",
                    amount = "10,000"
                )
            }
            item {
                TransactionCard(
                    transactionLetter = "B",
                    phoneNumber = "073323334",
                    transactionDateTime = "2 Dec 5:00pm",
                    amount = "-1000"
                )
            }
            item {
                TransactionCard(
                    transactionLetter = "S",
                    phoneNumber = "078392332",
                    transactionDateTime = "3 Jan 2:00pm",
                    amount = "1000"
                )
            }

        }
    }

}