package com.khor.smartpay.feature_cards.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khor.smartpay.R
import com.khor.smartpay.feature_cards.util.rememberQrBitmapPainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrCodeCard(
    modifier: Modifier = Modifier, qrCode: String
) {
    var showAlert by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(bottom = 20.dp)
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 20.dp)
                    .clip(RoundedCornerShape(16.dp)),
                painter = rememberQrBitmapPainter(content = "@smartpayuser1"),
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.limit),
                        contentDescription = null
                    )
                }
                Button(onClick = {
                    showAlert = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.freeze),
                        contentDescription = null
                    )
                }
                Button(onClick = {}) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        }

        Text(
            text = qrCode,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start
        )

        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                title = { Text(text = "Freeze", style = MaterialTheme.typography.headlineMedium) },
                text = { Switch(checked = true, onCheckedChange = {}) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showAlert = false
                        }) {
                        Text("Close")
                    }
                }
            )
        }
    }

}
