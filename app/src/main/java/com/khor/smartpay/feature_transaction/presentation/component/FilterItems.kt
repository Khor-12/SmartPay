package com.khor.smartpay.feature_auth.feature_transaction.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ChipFilter(
            sortTypeName = "All",
            onChipClicked = {
                viewModel.onEvent(TransactionsEvent.Order(TransactionOrder.All))
                viewModel.showTransactionDetails(TransactionOrder.All)
            },
            isSelected = transactionOrder is TransactionOrder.All
        )
        ChipFilter(
            sortTypeName = "Buy",
            onChipClicked = {
                viewModel.onEvent(TransactionsEvent.Order(TransactionOrder.Buy))
                viewModel.showTransactionDetails(TransactionOrder.Buy)
            },
            isSelected = transactionOrder is TransactionOrder.Buy
        )
        ChipFilter(
            sortTypeName = "Sell",
            onChipClicked = {
                viewModel.onEvent(TransactionsEvent.Order(TransactionOrder.Sell))
                viewModel.showTransactionDetails(TransactionOrder.Sell)
            },
            isSelected = transactionOrder is TransactionOrder.Sell
        )
        ChipFilter(
            sortTypeName = "Deposit",
            onChipClicked = {
                viewModel.onEvent(TransactionsEvent.Order(TransactionOrder.Deposit))
                viewModel.showTransactionDetails(TransactionOrder.Deposit)
            },
            isSelected = transactionOrder is TransactionOrder.Deposit
        )
        ChipFilter(
            sortTypeName = "Withdraw",
            onChipClicked = {
                viewModel.onEvent(TransactionsEvent.Order(TransactionOrder.Withdraw))
                viewModel.showTransactionDetails(TransactionOrder.Withdraw)
            },
            isSelected = transactionOrder is TransactionOrder.Withdraw
        )
    }
}