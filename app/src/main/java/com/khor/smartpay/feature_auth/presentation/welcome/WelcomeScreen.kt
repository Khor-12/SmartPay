package com.khor.smartpay.feature_auth.presentation.welcome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.khor.smartpay.core.util.Screen
import com.khor.smartpay.feature_auth.presentation.phone_number_input.components.SmartPayHeader

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(150.dp)
        ) {
            SmartPayHeader(
                modifier = Modifier.padding(top = 100.dp),
                subTitle = "Need Cashless Payment"
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp), verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                OutlinedButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text("Log in", modifier = Modifier.padding(8.dp))
                }
                Button(onClick = {
                    navController.navigate(Screen.UserSelectionScreen.route)
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Sign up", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}
