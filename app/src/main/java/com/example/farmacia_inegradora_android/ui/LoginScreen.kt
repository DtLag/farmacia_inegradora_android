package com.example.farmacia_inegradora_android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmacia_inegradora_android.view_models.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val primaryBlue = Color(0xFF3B5BA9)
    val lightBackground = Color(0xFFF5F7FA)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(lightBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(
                    primaryBlue,
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Farmacia Dr. Perez",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Panel de Administración",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Card de Login
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Iniciar Sesión",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Start)
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = { Text("Correo", color = Color.Black) },
                    placeholder = { Text("admin@gmail.com") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = primaryBlue,
                        unfocusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = primaryBlue) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    label = { Text("Contraseña", color = Color.Black) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = primaryBlue,
                        unfocusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = primaryBlue) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.login() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = primaryBlue,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "ACCEDER AL SISTEMA",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        )
                    }
                }

                uiState.errorMessage?.let { error ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = error,
                        color = Color(0xFFD32F2F),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        
        Text(
            text = "V 1.0.0 - Gestión Farmacéutica",
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LaunchedEffect(uiState.isSuccess, uiState.isAdmin) {
            if (uiState.isSuccess && uiState.isAdmin) {
                onLoginSuccess()
            }
        }
    }
}
