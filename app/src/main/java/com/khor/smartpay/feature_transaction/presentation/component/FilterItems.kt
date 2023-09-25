package com.khor.smartpay.feature_transaction.presentation.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khor.smartpay.feature_transaction.domain.util.TransactionOrder
import com.khor.smartpay.feature_transaction.presentation.TransactionDetailViewModel
import com.khor.smartpay.feature_transaction.presentation.TransactionsEvent
import com.khor.smartpay.feature_transaction.presentation.component.ChipFilter

@Composable
fun FilterItems(
    modifier: Modifier,
    viewModel: TransactionDetailViewModel,
    transactionOrder: TransactionOrder = TransactionOrder.All
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ChipFilter(
            modifier = Modifier,
            sortTypeName = "All",
            onChipClicked = {
                viewModel.onEvent(TransactionsEvent.Order(TransactionOrder.All))
                viewModel.showTransactionDetails(TransactionOrder.All)
            },
            isSelected = transactionOrder is TransactionOrder.All
        )
        ChipFilter(
            modifier = Modifier,
            sortTypeName = "Buy",
            onChipClicked = {
                viewModel.onEvent(TransactionsEvent.Order(TransactionOrder.Buy))
                viewModel.showTransactionDetails(TransactionOrder.Buy)
            },
            isSelected = transactionOrder is TransactionOrder.Buy
        )
        ChipFilter(
            modifier = Modifier,
            sortTypeName = "Sell",
            onChipClicked = {
                viewModel.onEvent(TransactionsEvent.Order(TransactionOrder.Sell))
                viewModel.showTransactionDetails(TransactionOrder.Sell)
            },
            isSelected = transactionOrder is TransactionOrder.Sell
        )
        ChipFilter(
            modifier = Modifier,
            sortTypeName = "Deposit",
            onChipClicked = {
                viewModel.onEvent(TransactionsEvent.Order(TransactionOrder.Deposit))
                viewModel.showTransactionDetails(TransactionOrder.Deposit)
            },
            isSelected = transactionOrder is TransactionOrder.Deposit
        )
        ChipFilter(
            modifier = Modifier.padding(end = 16.dp),
            sortTypeName = "Withdraw",
            onChipClicked = {
                viewModel.onEvent(TransactionsEvent.Order(TransactionOrder.Withdraw))
                viewModel.showTransactionDetails(TransactionOrder.Withdraw)
            },
            isSelected = transactionOrder is TransactionOrder.Withdraw
        )
    }
}