package com.khor.smartpay.feature_cards.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.khor.smartpay.R
import com.khor.smartpay.feature_cards.presentation.CardsViewModel
import com.khor.smartpay.feature_cards.util.rememberQrBitmapPainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrCodeCard(
    qrCode: String,
    isFrozen: Boolean,
    limit: Double,
    viewModel: CardsViewModel
) {
    var showAlertFreeze by remember { mutableStateOf(false) }
    var showAlertLimit by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        textFieldValue = limit.toString()
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
                Button(onClick = {
                    showAlertLimit = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.limit),
                        contentDescription = null
                    )
                }
                Button(onClick = {
                    showAlertFreeze = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.freeze),
                        contentDescription = null
                    )
                }
                Button(onClick = {
                    viewModel.deleteCard(qrCode)
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        }

        Text(
            text = qrCode,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start
        )

        if (showAlertFreeze) {
            AlertDialog(
                onDismissRequest = { showAlertFreeze = false },
                title = { Text(text = "Freeze", style = MaterialTheme.typography.headlineMedium) },
                text = {
                    Switch(checked = isFrozen, onCheckedChange = {
                        viewModel.updateCard(qrCode, !isFrozen, limit = null)
                    })
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showAlertFreeze = false
                        }) {
                        Text("Close")
                    }
                }
            )
        }

        if (showAlertLimit) {
            AlertDialog(
                onDismissRequest = { showAlertLimit = false },
                title = { Text(text = "Freeze", style = MaterialTheme.typography.headlineMedium) },
                text = {
                    TextField(
                        value = textFieldValue,
                        textStyle = TextStyle(fontSize = 28.sp),
                        onValueChange = { newValue ->
                            textFieldValue = newValue
                        }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.updateCard(
                                qrCode,
                                isFrozen = null,
                                limit = textFieldValue.toDouble()
                            )
                            showAlertLimit = false
                        }) {
                        Text("Save")
                    }
                }
            )
        }
    }

}
