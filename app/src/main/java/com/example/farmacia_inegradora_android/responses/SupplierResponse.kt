package com.example.farmacia_inegradora_android.responses

import com.example.farmacia_inegradora_android.models.Supplier

data class SupplierResponse(
    val suppliers : List<Supplier> = emptyList()
)
