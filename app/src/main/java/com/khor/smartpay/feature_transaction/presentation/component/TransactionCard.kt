package com.khor.smartpay.feature_transaction.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khor.smartpay.R
import com.khor.smartpay.feature_transaction.domain.model.TransactionDetail
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TransactionCard(
    transactionDetail: TransactionDetail,
    modifier: Modifier = Modifier
) {
    val transactionType = when {
        transactionDetail.transactionType.startsWith("D") ||
                transactionDetail.transactionType.startsWith("B") ->
            R.drawable.down_arrow

        transactionDetail.transactionType.startsWith("W") ||
                transactionDetail.transactionType.startsWith("S") ->
            R.drawable.up_arrow

        else -> 0
    }

    val transactionDetailType: String = when {
        transactionDetail.transactionType.startsWith("S") -> transactionDetail.to
        transactionDetail.transactionType.startsWith("B") -> transactionDetail.from
        transactionDetail.transactionType.startsWith("W") ||
                transactionDetail.transactionType.startsWith("D") ->
            transactionDetail.from

        else -> ""
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 4.dp)
            .then(modifier)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.9f)
                    .height(50.dp)
                    .width(50.dp)
                    .background(color = MaterialTheme.colorScheme.secondary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.onSecondary,
                    painter = painterResource(id = transactionType),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(16.dp)
            ) {
                Text(text = transactionDetailType, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(transactionDetail.dateTime, style = MaterialTheme.typography.bodySmall)
            }
            Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.CenterEnd) {
                Text(
                    text = "${
                        NumberFormat.getNumberInstance(Locale.US)
                            .format(transactionDetail.amount.toDouble())
                    } sh",
                    color = if (transactionDetail.amount.startsWith('-')) MaterialTheme.colorScheme.error
                    else Color(0xFF4CAF50),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
