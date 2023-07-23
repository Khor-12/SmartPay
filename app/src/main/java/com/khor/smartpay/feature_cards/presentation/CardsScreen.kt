package com.khor.smartpay.feature_cards.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khor.smartpay.R
import com.khor.smartpay.core.presentation.components.StandardToolbar
import com.khor.smartpay.feature_auth.presentation.verification.VerificationViewModel

@SuppressLint("ResourceType", "CoroutineCreationDuringComposition")
@Composable
fun CardsScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            title = { Text("Cards") },
            navActions = {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Add Card"
                    )
                }
            }
        )
    }
}