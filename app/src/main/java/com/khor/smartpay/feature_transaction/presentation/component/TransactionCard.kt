package com.khor.smartpay.feature_transaction.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.khor.smartpay.feature_transaction.domain.model.TransactionDetail

@Composable
fun TransactionCard(
    transactionDetail: TransactionDetail,
    modifier: Modifier = Modifier
) {
    val letter: String = when {
        transactionDetail.transactionType.startsWith("D") -> "D"
        transactionDetail.transactionType.startsWith("W") -> "W"
        transactionDetail.transactionType.startsWith("B") -> "B"
        transactionDetail.transactionType.startsWith("S") -> "S"
        else -> ""
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 4.dp)
            .then(modifier),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.8f)
                    .height(50.dp)
                    .width(50.dp)
                    .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = letter,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(16.dp)
            ) {
                Text(text = transactionDetail.to, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(transactionDetail.dateTime, style = MaterialTheme.typography.bodySmall)
            }
            Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.CenterEnd) {
                Text(
                    text = transactionDetail.amount,
                    color = if (transactionDetail.amount.startsWith('-')) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
