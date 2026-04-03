package com.example.farmacia_inegradora_android.ui_state

import com.example.farmacia_inegradora_android.models.Product

data class InventoryUIState(
    val isLoading: Boolean = false,
    val productsList: List<Product> = emptyList(),
    val filteredProductsList: List<Product> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null
)