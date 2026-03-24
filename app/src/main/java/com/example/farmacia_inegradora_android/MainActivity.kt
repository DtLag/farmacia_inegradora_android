package com.example.farmacia_inegradora_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmacia_inegradora_android.ui.DashboardScreen
import com.example.farmacia_inegradora_android.ui.LoginScreen
import com.example.farmacia_inegradora_android.ui.theme.Farmacia_inegradora_androidTheme
import com.example.farmacia_inegradora_android.view_models.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Farmacia_inegradora_androidTheme {
                val loginViewModel: LoginViewModel = viewModel()
                val uiState by loginViewModel.uiState.collectAsState()

                if (uiState.isSuccess && uiState.isAdmin) {
                    DashboardScreen(
                        userName = uiState.userData?.name ?: "Usuario",
                        onLogout = { loginViewModel.logout() }
                    )
                } else {
                    LoginScreen(
                        viewModel = loginViewModel,
                        onLoginSuccess = {
                        }
                    )
                }
            }
        }
    }
}
