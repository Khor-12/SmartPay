package com.khor.smartpay.feature_home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DropdownMenuDemo(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Option 1") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Selected Option: $selectedOption",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )

        // Create a DropdownMenu with DropdownMenuItems
        Box(
            modifier = Modifier
                .padding(16.dp)
                .width(200.dp)
        ) {
            // Open/close the dropdown menu
            TextButton(
                onClick = { expanded = !expanded }
            ) {
                Text(text = "Select Option")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // Create DropdownMenuItems
                DropdownMenuItem(
                    onClick = {
                        selectedOption = "Option 1"
                        expanded = false
                    }
                ) {
                    Text(text = "Option 1")
                }
                DropdownMenuItem(
                    onClick = {
                        selectedOption = "Option 2"
                        expanded = false
                    }
                ) {
                    Text(text = "Option 2")
                }
                DropdownMenuItem(
                    onClick = {
                        selectedOption = "Option 3"
                        expanded = false
                    }
                ) {
                    Text(text = "Option 3")
                }
            }
        }
    }
}
