package com.example.farmacia_inegradora_android.responses

import com.example.farmacia_inegradora_android.models.RestockItem

data class RestockResponse(
    val success: Boolean,
    val data: List<RestockItem>
)
