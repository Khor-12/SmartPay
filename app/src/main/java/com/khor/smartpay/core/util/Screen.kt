package com.khor.smartpay.core.util

sealed class Screen(val route: String) {
    object WelcomeScreen: Screen("welcome_screen")
    object VerificationScreen: Screen("verification_screen")
    object HomeScreen: Screen("home_screen")
    object PaymentScreen: Screen("payment_screen")
    object CardsScreen: Screen("cards_screen")
    object TransactionsScreen: Screen("Transactions_screen")
    object InternalScreen: Screen("internal_screen")
}