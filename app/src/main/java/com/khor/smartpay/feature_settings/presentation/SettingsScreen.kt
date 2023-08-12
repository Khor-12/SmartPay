package com.khor.smartpay.feature_settings.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.khor.smartpay.R
import com.khor.smartpay.core.presentation.components.CustomTopAppBar

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val state = viewModel.state

    CustomTopAppBar(
        navController = navController,
        icon = Icons.Default.ArrowBack,
        title = "Settings"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(54.dp))
        Text(
            modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp),
            text = "Account",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
                .clickable {  },
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
                .clickable { },
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
            modifier = Modifier
                .fillMaxWidth(),
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
                        .padding(start = 24.dp, top = 18.dp, bottom = 18.dp, end = 8.dp)
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
                painter = painterResource(R.drawable.help_center), contentDescription = null,
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
                painter = painterResource(R.drawable.terms), contentDescription = null,
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
                .clickable { },
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
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SmartPay v1.0.0",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }


}