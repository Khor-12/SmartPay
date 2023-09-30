package com.khor.smartpay.core.util

sealed class Screen(val route: String) {
    object PhoneNumberInputScreen: Screen("phone_number_input_screen")
    object WelcomeScreen: Screen("welcome_screen")
    object VerificationScreen: Screen("verification_screen")
    object HomeScreen: Screen("home_screen")
    object HomeScreenSeller: Screen("home_screen_seller")
    object PaymentScreen: Screen("payment_screen")
    object CardsScreen: Screen("cards_screen")
    object TransactionsScreen: Screen("Transactions_screen")
    object InternalScreen: Screen("internal_screen")
    object InternalScreenSeller: Screen("internal_screen_seller")
    object SettingsScreen: Screen("settings_screen")
    object UserSelectionScreen: Screen("user_selection_screen")
    object CreateCodeScreen: Screen("create_code_screen")
    object EnterCodeScreen: Screen("enter_code_screen")
    object ConfirmCodeScreen: Screen("confirm_code_screen")
    object GenerateQrCodeScreen: Screen("generate_qrcode_screen")
}