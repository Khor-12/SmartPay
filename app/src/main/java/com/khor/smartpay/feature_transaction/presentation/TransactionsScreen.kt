package com.khor.smartpay.feature_transaction.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khor.smartpay.R
import com.khor.smartpay.core.presentation.components.StandardToolbar
import com.khor.smartpay.feature_auth.feature_transaction.presentation.component.FilterItems
import com.khor.smartpay.feature_transaction.presentation.component.TransactionCard

@Composable
fun TransactionsScreen() {
    val viewModel: TransactionDetailViewModel = hiltViewModel()
    val results = viewModel.state.value.transactionDetailItems
    val state = viewModel.state

    StandardToolbar(
        title = { Text("Transactions") },
        modifier = Modifier.padding(bottom = 8.dp),
        navActions = {
            IconButton(
                onClick = { viewModel.onEvent(TransactionsEvent.ToggleOrderSection) },
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

    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = state.value.isFilterListVisible) {
            FilterItems(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                viewModel = viewModel,
                transactionOrder = viewModel.state.value.transactionOrder
            )
        }

        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            if (viewModel.state.value.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize().offset(y = (-50).dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(70.dp))
                }
            } else {
                if (viewModel.state.value.transactionDetailItems.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No Transactions", modifier = Modifier.offset(y = (-50).dp))
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {

                        itemsIndexed(items = results) { index, item ->
                            TransactionCard(
                                item
                            )
                            if (index == results.lastIndex) {
                                // Apply additional padding to the last item
                                Spacer(modifier = Modifier.padding(bottom = 150.dp)) // Change bottom padding as needed
                            }
                        }
                    }
                }

            }
        }

    }

}