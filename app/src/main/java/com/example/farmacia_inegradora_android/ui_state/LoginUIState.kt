package com.example.farmacia_inegradora_android.ui_state

import com.example.farmacia_inegradora_android.responses.UserData

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val isAdmin: Boolean = false,
    val userData: UserData? = null
)
