package com.example.farmacia_inegradora_android.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmacia_inegradora_android.data.PharmacyRepository
import com.example.farmacia_inegradora_android.data.SessionManager
import com.example.farmacia_inegradora_android.requests.LoginRequest
import com.example.farmacia_inegradora_android.ui_state.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    private val repo = PharmacyRepository
    private val sessionManager = SessionManager(application)

    init {
        // Al iniciar, verificamos si ya hay una sesión guardada
        if (sessionManager.isLoggedIn()) {
            val user = sessionManager.getUser()
            _uiState.update { it.copy(
                isSuccess = true,
                isAdmin = user?.role == "admin",
                userData = user
            ) }
        }
    }

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun logout() {
        sessionManager.clearSession()
        _uiState.update { 
            LoginUIState() 
        }
    }

    fun login() {
        val currentState = _uiState.value
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Por favor, completa todos los campos") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val response = repo.login(LoginRequest(currentState.email, currentState.password))
                
                val user = response.user
                val token = response.token
                
                if (user != null && token != null) {
                    if (user.role == "admin") {
                        // Guardamos la sesión localmente
                        sessionManager.saveSession(token, user)
                        
                        _uiState.update { it.copy(
                            isLoading = false, 
                            isSuccess = true, 
                            isAdmin = true,
                            userData = user
                        ) }
                    } else {
                        _uiState.update { it.copy(isLoading = false, errorMessage = "Acceso denegado: Solo administradores.") }
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Error: No se recibió información completa") }
                }
                
            } catch (e: HttpException) {
                val errorMsg = when (e.code()) {
                    401 -> "Credenciales incorrectas"
                    403 -> "No tienes permisos"
                    else -> "Error en el servidor: ${e.code()}"
                }
                _uiState.update { it.copy(isLoading = false, errorMessage = errorMsg) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Error de conexión: Verifica tu red") }
            }
        }
    }
}
