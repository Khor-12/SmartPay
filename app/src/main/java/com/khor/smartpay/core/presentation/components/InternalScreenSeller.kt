package com.khor.smartpay.core.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.khor.smartpay.R
import com.khor.smartpay.feature_cards.presentation.CardsScreen
import com.khor.smartpay.feature_home.domain.repository.EasyPayApi
import com.khor.smartpay.feature_home.presentation.HomeScreen
import com.khor.smartpay.feature_home.presentation.HomeScreenSeller
import com.khor.smartpay.feature_payment.presentation.PaymentScreen
import com.khor.smartpay.feature_transaction.presentation.TransactionsScreen
import com.khor.smartpay.feature_transaction.presentation.TransactionsScreenSeller

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppContentSeller(navController: NavController, easyPayApi: EasyPayApi) {
    var currentScreen by rememberSaveable { mutableStateOf(InternalScreenSeller.Home) }

    Scaffold(
        bottomBar = {
            BottomNavigationBarSeller(currentScreen = currentScreen) { screen ->
                currentScreen = screen
            }
        }
    ) {
        ScreenContentSeller(currentScreen, navController, easyPayApi)
    }
}

@Composable
fun BottomNavigationBarSeller(currentScreen: InternalScreenSeller, onScreenSelected: (InternalScreenSeller) -> Unit) {
    NavigationBar() {
        NavigationBarItem(
            selected = currentScreen == InternalScreenSeller.Home,
            onClick = { onScreenSelected(InternalScreenSeller.Home) },
            icon = {
                Icon(
                    Icons.Outlined.Home,
                    modifier = Modifier.size(28.dp),
                    contentDescription = "Home"
                )
            },
        )
        NavigationBarItem(
            selected = currentScreen == InternalScreenSeller.Payment,
            onClick = { onScreenSelected(InternalScreenSeller.Payment) },
            icon = {
                Icon(
                    painterResource(id = R.drawable.payments),
                    contentDescription = "Payments"
                )
            }
        )
        NavigationBarItem(
            selected = currentScreen == InternalScreenSeller.Transactions,
            onClick = {
                onScreenSelected(InternalScreenSeller.Transactions)
            },
            icon = {
                Icon(
                    painterResource(id = R.drawable.transactions),
                    contentDescription = "Transactions"
                )
            }
        )
    }
}

@Composable
fun ScreenContentSeller(screen: InternalScreenSeller, navController: NavController, easyPayApi: EasyPayApi) {
    Column {
        when (screen) {
            InternalScreenSeller.Home -> {
                HomeScreenSeller(navController, easyPayApi)
            }

            InternalScreenSeller.Payment -> {
                PaymentScreen()
            }

            InternalScreenSeller.Transactions -> {
                TransactionsScreenSeller()
            }
        }
    }
}

enum class InternalScreenSeller {
    Home, Payment, Transactions
}