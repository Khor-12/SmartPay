package com.khor.smartpay.core.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.dataStore
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.khor.smartpay.SmartPayApplication
import com.khor.smartpay.core.data.prefdatastore.UserStore
import com.khor.smartpay.core.presentation.components.Navigation
import com.khor.smartpay.core.presentation.ui.theme.SmartPayTheme
import com.khor.smartpay.core.util.AppSettings
import com.khor.smartpay.core.util.AppSettingsSerializer
import com.khor.smartpay.core.util.Screen
import com.khor.smartpay.feature_auth.presentation.confirm_code.ConfirmCode
import com.khor.smartpay.feature_auth.presentation.create_code.CreateCode
import com.khor.smartpay.feature_auth.presentation.enter_code.EnterCode
import com.khor.smartpay.feature_auth.presentation.user_selection.UserSelection
import com.khor.smartpay.feature_auth.presentation.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<MainViewModel>()

   val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)

    @SuppressLint("RememberReturnType", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        val moduleInstall = ModuleInstall.getClient(this)
        val moduleInstallRequest = ModuleInstallRequest.newBuilder()
            .addApi(GmsBarcodeScanning.getClient(this))
            .build()
        moduleInstall
            .installModules(moduleInstallRequest)
            .addOnSuccessListener {
                if (it.areModulesAlreadyInstalled()) {
                    // Modules are already installed when the request is sent.
                }
            }
            .addOnFailureListener {
                // Handle failure…
            }
        val easyPayApi = (application as SmartPayApplication).easyPayApi

        setContent {
            val store = UserStore(LocalContext.current)
            LaunchedEffect(key1 = Unit) {
//                viewModel.getColorScheme(store)
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
                    Navigation(
                        navController = navController,
                        easyPayApi = easyPayApi
                    )
                    AuthState()
                }
            }
        }

    }

    @SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
    @Composable
    private fun AuthState() {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        val userType = viewModel.state.userType
        println("The user is $isUserSignedOut")

        if (!isUserSignedOut) {
            NavigateToSignInScreen()
        } else {
            if (userType == "parent") {
                NavigateToInternalScreen()
            } else if (userType == "seller") {
                NavigateToInternalScreenSeller()
            } else {
            }
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

    @Composable
    private fun NavigateToInternalScreenSeller() = navController.navigate(Screen.InternalScreenSeller.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE =
            1001 // You can use any integer value here
    }

}
