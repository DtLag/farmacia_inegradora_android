package com.example.farmacia_inegradora_android.ui_state

import com.example.farmacia_inegradora_android.models.Sale

data class SalesUIState (
    val isLoading: Boolean = false,
    val allSales: List<Sale> = emptyList(),
    val filteredSales: List<Sale> = emptyList(),
    val filterType: String = "Todas",
    val startDate: String = "",
    val endDate: String = "",
    val errorMessage: String? = null
)