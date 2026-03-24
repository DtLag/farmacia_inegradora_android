package com.example.farmacia_inegradora_android.responses

data class LoginResponse(
    // Como tu Controller usa LoginResponseResource, 
    // es probable que la estructura sea esta:
    val user: UserData?,
    val token: String?,
    // Agregamos success por seguridad, aunque Laravel Resource no lo pone por defecto
    // Si tu Resource no lo tiene, lo podemos omitir o manejar en el ViewModel
    val success: Boolean = true
)
