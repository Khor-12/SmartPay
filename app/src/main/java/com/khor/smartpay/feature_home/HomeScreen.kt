package com.khor.smartpay.feature_home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
    StandardToolbar(title = { Text("+256787102643", style = MaterialTheme.typography.titleMedium)}, navActions = {
        IconButton(
            onClick = {

            }, modifier = Modifier
        ) {
            Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
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
                text = "Balance",
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
        Text(text = "Analytics", style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.height(16.dp))
        HistogramGraph(listOf(15f, 35f, 50f, 80f, 65f, 30f))
    }

}

@Composable
fun HistogramGraph(data: List<Float>) {
    val maxValue = data.maxOrNull() ?: 0f
    val barWidth = 30.dp
    val barSpacing = 16.dp

    Canvas(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val xStep = (size.width - (data.size - 1) * barSpacing.toPx()) / data.size
        var x = 0f

        data.forEachIndexed { index, value ->
            val barHeight = (value / maxValue) * size.height

            drawRect(
                color = Color.Blue,
                topLeft = Offset(x, size.height - barHeight),
                size = Size(barWidth.toPx(), barHeight),
                style = Stroke(width = 1.dp.toPx())
            )

            x += xStep + barSpacing.toPx()
        }
    }
}
