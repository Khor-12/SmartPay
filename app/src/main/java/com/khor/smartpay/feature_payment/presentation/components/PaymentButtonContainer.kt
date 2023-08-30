package com.khor.smartpay.feature_payment.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.khor.smartpay.feature_payment.presentation.PaymentScreenEvent
import com.khor.smartpay.feature_payment.presentation.PaymentUiAction

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PaymentButtonContainer(
    actions: List<PaymentUiAction>,
    modifier: Modifier,
    onEvent: (PaymentScreenEvent) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
        content = {
            items(actions) { action ->
                if (action.text != null) {
                    PaymentButton(
                        value = action.text,
                        modifier = Modifier.aspectRatio(1f),
                        onClick = { }
                    )
                }
            }
        }
    )

}

