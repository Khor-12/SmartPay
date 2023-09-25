package com.khor.smartpay.feature_transaction.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipFilter(
    modifier: Modifier = Modifier,
    sortTypeName: String,
    onChipClicked: () -> Unit,
    isSelected: Boolean
) {
    FilterChip(modifier = modifier,
        selected = isSelected,
        onClick = { onChipClicked() },
        label = {
            Text(
                sortTypeName,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}