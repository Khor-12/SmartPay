package com.khor.smartpay.feature_settings.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.khor.smartpay.R
import com.khor.smartpay.core.data.prefdatastore.UserStore
import com.khor.smartpay.core.presentation.components.CustomTopAppBar
import com.khor.smartpay.core.presentation.selectedColorScheme
import com.khor.smartpay.feature_settings.presentation.component.DialogItem
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val state = viewModel.state
    var openDialog by remember { mutableStateOf(false) }
    val store = UserStore(LocalContext.current)
    val colorSchemeOptions = listOf("System Theme", "Dark Theme", "Light Theme")

    CustomTopAppBar(
        navController = navController, icon = Icons.Default.ArrowBack, title = "Settings"
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(54.dp))
        Text(
            modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp),
            text = "Account",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                painter = painterResource(R.drawable.contact),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 24.dp, top = 18.dp, bottom = 18.dp, end = 8.dp)
                    .size(28.dp)
            )
            Text(
                text = state.phoneNumber,
                modifier = Modifier.padding(end = 24.dp, top = 18.dp, bottom = 18.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.deleteUser(store)
                    viewModel.onEvent(SettingsEvents.SignOutUser)
                },
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
                painter = painterResource(R.drawable.delete_forever),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 24.dp, top = 18.dp, bottom = 18.dp, end = 8.dp)
                    .size(28.dp)
            )
            Text(
                text = "Delete Account",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(end = 24.dp, top = 18.dp, bottom = 18.dp)
            )
        }
        Text(
            "App",
            modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { openDialog = true },
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                painter = painterResource(R.drawable.color_scheme),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 24.dp, top = 18.dp, bottom = 18.dp, end = 8.dp)
                    .size(28.dp)
            )
            Text(
                text = "Color Scheme",
                modifier = Modifier.padding(end = 24.dp, top = 18.dp, bottom = 18.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                    painter = painterResource(R.drawable.notifications),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(
                            start = 24.dp, top = 18.dp, bottom = 18.dp, end = 8.dp
                        )
                        .size(28.dp)
                )
                Text(
                    text = "Notifications",
                    modifier = Modifier.padding(end = 24.dp, top = 18.dp, bottom = 18.dp)
                )
            }
            Switch(
                checked = false,
                onCheckedChange = {},
                modifier = Modifier
                    .size(18.dp)
                    .offset(x = (-40).dp)
            )
        }
        Text(
            "About",
            modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                painter = painterResource(R.drawable.help_center),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 24.dp, top = 18.dp, bottom = 18.dp, end = 8.dp)
                    .size(28.dp)
            )
            Text(
                text = "Help Center",
                modifier = Modifier.padding(end = 24.dp, top = 18.dp, bottom = 18.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                painter = painterResource(R.drawable.terms),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 24.dp, top = 18.dp, bottom = 18.dp, end = 8.dp)
                    .size(28.dp)
            )
            Text(
                text = "Terms of User",
                modifier = Modifier.padding(end = 24.dp, top = 18.dp, bottom = 18.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                painter = painterResource(R.drawable.privacy_policy),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 24.dp, top = 18.dp, bottom = 18.dp, end = 8.dp)
                    .size(28.dp)
            )
            Text(
                text = "Privacy Policy",
                modifier = Modifier.padding(end = 24.dp, top = 18.dp, bottom = 18.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.onEvent(SettingsEvents.SignOutUser)
                    viewModel.viewModelScope.launch {
                        store.clearDataStore()
                    }
                },
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
                painter = painterResource(R.drawable.sign_out),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 24.dp, top = 18.dp, bottom = 18.dp, end = 8.dp)
                    .size(28.dp)
            )
            Text(
                text = "Sign Out",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(end = 24.dp, top = 18.dp, bottom = 18.dp)
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SmartPay v1.0.0",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    if (openDialog) {
        AlertDialog(onDismissRequest = { openDialog = false }, text = {
            Column {
                DialogItem(text = "System (Default)",
                    isSelected = selectedColorScheme == 2,
                    onSelected = {
                        selectedColorScheme = 2
                        openDialog = false
                    }
                )
                DialogItem(text = "Light",
                    isSelected = selectedColorScheme == 1,
                    onSelected = {
                        selectedColorScheme = 1
                        openDialog = false
                    }
                )
                DialogItem(text = "Dark",
                    isSelected = selectedColorScheme == 0,
                    onSelected = {
                        selectedColorScheme = 0
                        openDialog = false
                    }
                )
            }
        }, confirmButton = {})
    }

}

