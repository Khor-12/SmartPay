package com.khor.smartpay.core.util

sealed class Screen(val route: String) {
    object WelcomeScreen: Screen("welcome_screen")
    object VerificationScreen: Screen("verification_screen")
    object QRCodeScanScreen: Screen("qr_code_scan_screen")
}