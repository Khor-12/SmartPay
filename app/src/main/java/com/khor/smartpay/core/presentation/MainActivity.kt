package com.khor.smartpay.core.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
            val store = UserStore(LocalContext.current)
            LaunchedEffect(key1 = Unit) {
                viewModel.getColorScheme(store)
            }
            store.getAccessToken
            SmartPayTheme(
//                viewModel.state.currentTheme == 2
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navController = rememberNavController()
                    Navigation(navController = navController)
                    AuthState()
                }
            }
        }
    }

    @Composable
    private fun AuthState() {
        val store = UserStore(LocalContext.current)
        val isUserSignedOut = viewModel.getAuthState(store).collectAsState().value
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

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE =
            1001 // You can use any integer value here
    }

}
