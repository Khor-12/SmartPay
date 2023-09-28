package com.khor.smartpay.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.khor.smartpay.core.util.Screen
import com.khor.smartpay.feature_auth.presentation.confirm_code.ConfirmCode
import com.khor.smartpay.feature_auth.presentation.create_code.CreateCode
import com.khor.smartpay.feature_auth.presentation.enter_code.EnterCode
import com.khor.smartpay.feature_auth.presentation.phone_number_input.PhoneNumberInputScreen
import com.khor.smartpay.feature_auth.presentation.verification.VerificationScreen
import com.khor.smartpay.feature_auth.presentation.user_selection.UserSelection
import com.khor.smartpay.feature_auth.presentation.welcome.WelcomeScreen
import com.khor.smartpay.feature_cards.presentation.CardsScreen
import com.khor.smartpay.feature_home.domain.repository.EasyPayApi
import com.khor.smartpay.feature_home.presentation.HomeScreen
import com.khor.smartpay.feature_home.presentation.HomeScreenSeller
import com.khor.smartpay.feature_payment.presentation.PaymentScreen
import com.khor.smartpay.feature_settings.presentation.SettingsScreen
import com.khor.smartpay.feature_transaction.presentation.TransactionsScreen

@Composable
fun Navigation(
    navController: NavHostController,
    easyPayApi: EasyPayApi
) {
    NavHost(
        navController = navController,
        startDestination = Screen.WelcomeScreen.route
    ) {
        composable(
            route = Screen.InternalScreen.route
        ) {
            AppContent(navController, easyPayApi = easyPayApi)
        }
        composable(
            route = Screen.WelcomeScreen.route
        ) {
            WelcomeScreen(
                navController
//                navigateToVerificationScreen = { verificationId, phoneNumber ->
//                    navController.navigate(Screen.VerificationScreen.route + "/$verificationId/$phoneNumber")
//                }
            )
        }
        composable(
            route = Screen.PhoneNumberInputScreen.route + "/{userType}",
            arguments = listOf(
                navArgument("userType") {
                    type = NavType.StringType
                }
            )
        ) {
            val userType = it.arguments?.getString("userType")!!

            PhoneNumberInputScreen(
                navigateToVerificationScreen = { verificationId, phoneNumber ->
                    navController.navigate(Screen.VerificationScreen.route + "/$verificationId/$phoneNumber/$userType")
                },
                userType = userType,
                navController = navController
            )
        }
        composable(
            route = Screen.VerificationScreen.route + "/{verificationId}/{phoneNumber}/{userType}",
            arguments = listOf(
                navArgument("verificationId") {
                    type = NavType.StringType
                },
                navArgument("phoneNumber") {
                    type = NavType.StringType
                },
                navArgument("userType") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            VerificationScreen(
                navController = navController,
                verificationId = entry.arguments?.getString("verificationId")!!,
                phoneNumber = entry.arguments?.getString("phoneNumber")!!,
                userType = entry.arguments?.getString("userType")!!
            )
        }

        composable(
            route = Screen.EnterCodeScreen.route
        ) {
            EnterCode(navController)
        }

        composable(
            route = Screen.CreateCodeScreen.route
        ) {
            CreateCode(navController)
        }

        composable(
            route = Screen.ConfirmCodeScreen.route + "/{pinCode}",
            arguments = listOf(
                navArgument("pinCode") {
                    type = NavType.StringType
                }
            )
        ) {
            ConfirmCode(navController = navController, pinCode = it.arguments?.getString("pinCode")!!)
        }

        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(navController, easyPayApi)
        }

        composable(
            route = Screen.HomeScreenSeller.route
        ) {
            HomeScreenSeller(navController, easyPayApi)
        }

        composable(
            route = Screen.PaymentScreen.route
        ) {
            PaymentScreen()
        }
        composable(
            route = Screen.CardsScreen.route
        ) {
            CardsScreen()
        }
        composable(
            route = Screen.TransactionsScreen.route
        ) {
            TransactionsScreen()
        }

        composable(
            route = Screen.SettingsScreen.route
        ) {
            SettingsScreen(navController)
        }

        composable(
            route = Screen.UserSelectionScreen.route
        ) {
            UserSelection(navController)
        }
    }
}
