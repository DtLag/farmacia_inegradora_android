package com.example.farmacia_inegradora_android.responses

import com.example.farmacia_inegradora_android.models.Category

data class CategoryResponse(
    val categories : List<Category> = emptyList()
)
