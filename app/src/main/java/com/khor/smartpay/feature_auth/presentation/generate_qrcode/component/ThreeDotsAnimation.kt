package com.khor.smartpay.feature_auth.presentation.generate_qrcode.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ThreeDotsLoadingAnimation(
    modifier: Modifier = Modifier,
    dotColor: Color = Color.Gray,
    dotRadius: Float = 10f,
    animationDurationMillis: Int = 200
) {
    var animationState by remember { mutableStateOf(0) }

    val transition = rememberInfiniteTransition()

    val dotScale by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDurationMillis),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .size(24.dp)
                .aspectRatio(1f)
        ) {
            val centerY = size.height / 2f
            val centerX = size.width / 2f
            val spacing = 30f

            for (i in 0 until 3) {
                drawCircle(
                    color = dotColor,
                    radius = dotRadius,
                    center = Offset((centerX - (spacing * 1.5) + spacing * i).toFloat(), centerY),
                    alpha = if (i == animationState) 1f else 0.4f
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(animationDurationMillis.toLong())
            animationState = (animationState + 1) % 3
        }
    }
}
