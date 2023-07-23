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
import androidx.hilt.navigation.compose.hiltViewModel
import com.khor.smartpay.R
import com.khor.smartpay.feature_cards.presentation.CardsScreen
import com.khor.smartpay.feature_home.HomeScreen
import com.khor.smartpay.feature_payment.PaymentScreen
import com.khor.smartpay.feature_transaction.presentation.TransactionDetailViewModel
import com.khor.smartpay.feature_transaction.presentation.TransactionsScreen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppContent() {
    var currentScreen by rememberSaveable { mutableStateOf(InternalScreen.Home) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(currentScreen = currentScreen) { screen ->
                currentScreen = screen
            }
        }
    ) {
        ScreenContent(currentScreen)
    }
}

@Composable
fun BottomNavigationBar(currentScreen: InternalScreen, onScreenSelected: (InternalScreen) -> Unit) {
    NavigationBar() {
        NavigationBarItem(
            selected = currentScreen == InternalScreen.Home,
            onClick = { onScreenSelected(InternalScreen.Home) },
            icon = {
                Icon(
                    Icons.Outlined.Home,
                    modifier = Modifier.size(28.dp),
                    contentDescription = "Home"
                )
            },
        )
        NavigationBarItem(
            selected = currentScreen == InternalScreen.Payment,
            onClick = { onScreenSelected(InternalScreen.Payment) },
            icon = {
                Icon(
                    painterResource(id = R.drawable.payments),
                    contentDescription = "Payments"
                )
            }
        )
        NavigationBarItem(
            selected = currentScreen == InternalScreen.Cards,
            onClick = { onScreenSelected(InternalScreen.Cards) },
            icon = { Icon(painterResource(id = R.drawable.cards), contentDescription = "Cards") }
        )
        NavigationBarItem(
            selected = currentScreen == InternalScreen.Transactions,
            onClick = {
                onScreenSelected(InternalScreen.Transactions)
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
fun ScreenContent(screen: InternalScreen) {
    Column {
        when (screen) {
            InternalScreen.Home -> {
                HomeScreen()
            }

            InternalScreen.Payment -> {
                PaymentScreen()
            }

            InternalScreen.Cards -> {
                CardsScreen()
            }

            InternalScreen.Transactions -> {
                TransactionsScreen()
            }
        }
    }
}

enum class InternalScreen {
    Home, Payment, Cards, Transactions
}