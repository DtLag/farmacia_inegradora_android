package com.example.farmacia_inegradora_android.responses

import com.example.farmacia_inegradora_android.models.Sale

data class SalesResponse(
    val success: Boolean = true,
    val message: String? = null,
    val data: List<Sale> = emptyList()
)