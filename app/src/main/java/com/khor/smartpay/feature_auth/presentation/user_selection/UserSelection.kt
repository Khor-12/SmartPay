package com.khor.smartpay.feature_auth.presentation.user_selection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.khor.smartpay.core.util.Screen

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserSelection(navController: NavController) {
    var selectedChipOne by remember {
        mutableStateOf(false)
    }
    var selectedChipTwo by remember {
        mutableStateOf(false)
    }
    var userType by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 100.dp),
            text = "I'm a",
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(100.dp))
        FilterChip(
            selected = selectedChipOne,
            onClick = {
                selectedChipTwo = false
                selectedChipOne = true
                userType = "seller"
                navController.navigate(Screen.PhoneNumberInputScreen.route + "/$userType")
            },
            label = {
                Text(
                    "Seller",
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }
        )
        FilterChip(
            selected = selectedChipTwo,
            onClick = {
                selectedChipOne = false
                selectedChipTwo = true
                userType = "parent"
                navController.navigate(Screen.PhoneNumberInputScreen.route + "/$userType")
            },
            label = {
                Text(
                    "Parent/Student"
                )
            }
        )
    }
}