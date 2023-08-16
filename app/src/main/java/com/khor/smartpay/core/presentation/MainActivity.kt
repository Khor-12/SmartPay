package com.khor.smartpay.core.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.khor.smartpay.core.data.prefdatastore.UserStore
import com.khor.smartpay.core.presentation.components.Navigation
import com.khor.smartpay.core.presentation.ui.theme.SmartPayTheme
import com.khor.smartpay.core.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("RememberReturnType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartPayTheme(
                darkTheme = UserStore(LocalContext.current).getColorScheme.collectAsState(
                    initial = 0
                ).value == 2
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val store = UserStore(LocalContext.current)
                    val tokenBoolean = store.getAccessToken.collectAsState(initial = true).value
                    navController = rememberNavController()
                    Navigation(navController = navController)
                    if (tokenBoolean) {
                        AuthState()
                    }
                }
            }
        }
    }

    @Composable
    private fun AuthState() {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        if (isUserSignedOut) {
            NavigateToSignInScreen()
        } else {
            NavigateToInternalScreen()
        }
    }

    @Composable
    private fun NavigateToSignInScreen() = navController.navigate(Screen.WelcomeScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToInternalScreen() = navController.navigate(Screen.InternalScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}
