package com.example.farmacia_inegradora_android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmacia_inegradora_android.models.Sale
import com.example.farmacia_inegradora_android.view_models.SalesViewModel

@Composable
fun SalesScreen(
    viewModel: SalesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val primaryBlue = Color(0xFF3B5BA9)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Reporte de Ventas", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            val options = listOf("Todas", "Mostrador", "Pickup")
            options.forEach { type ->
                FilterButton(
                    text = type,
                    isSelected = uiState.filterType == type,
                    onClick = { viewModel.updateFilterType(type) }
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = uiState.startDate,
                onValueChange = { viewModel.updateDates(it, uiState.endDate) },
                label = { Text("Desde (YYYY-MM-DD)") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            OutlinedTextField(
                value = uiState.endDate,
                onValueChange = { viewModel.updateDates(uiState.startDate, it) },
                label = { Text("Hasta (YYYY-MM-DD)") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = primaryBlue)
                }
            }
            uiState.errorMessage != null -> {
                Text(text = uiState.errorMessage!!, color = Color.Red)
            }
            uiState.filteredSales.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay ventas con estos filtros.", color = Color.Gray)
                }
            }
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(uiState.filteredSales) { sale ->
                        SaleItemCard(sale)
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF3B5BA9) else Color.LightGray,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text)
    }
}

@Composable
fun SaleItemCard(sale: Sale) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Venta #${sale.id}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "Fecha: ${sale.date}", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = sale.sale_type.uppercase(),
                    color = if(sale.sale_type.lowercase() == "pickup") Color(0xFFE65100) else Color(0xFF1565C0),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
            Text(text = "$${sale.total}", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF2E7D32))
        }
    }
}