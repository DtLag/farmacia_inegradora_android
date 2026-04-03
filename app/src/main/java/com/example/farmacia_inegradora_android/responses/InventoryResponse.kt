package com.example.farmacia_inegradora_android.responses

import com.example.farmacia_inegradora_android.models.Product

data class InventoryResponse(
    val success: Boolean = true,
    val message: String? = null,
    val data: List<Product> = emptyList() // Aquí está la lista real que nos importa
)