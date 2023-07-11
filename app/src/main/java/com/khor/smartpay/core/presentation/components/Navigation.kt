package com.khor.smartpay.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.khor.smartpay.core.util.Screen
import com.khor.smartpay.feature_auth.presentation.verification.VerificationScreen
import com.khor.smartpay.feature_auth.presentation.welcome.WelcomeScreen

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.WelcomeScreen.route
    ) {
        composable(
            route = Screen.WelcomeScreen.route
        ) {
            WelcomeScreen(
                navigateToVerificationScreen = { verificationId, phoneNumber ->
                    navController.navigate(Screen.VerificationScreen.route + "/$verificationId/$phoneNumber")
                }
            )
        }
        composable(
            route = Screen.VerificationScreen.route + "/{verificationId}/{phoneNumber}",
            arguments = listOf(
                navArgument("verificationId") {
                    type = NavType.StringType
                },
                navArgument("verificationId") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            VerificationScreen(
                verificationId = entry.arguments?.getString("verificationId")!!,
                phoneNumber = entry.arguments?.getString("phoneNumber")!!
            )
        }
    }
}