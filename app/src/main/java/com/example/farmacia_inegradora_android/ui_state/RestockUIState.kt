package com.example.farmacia_inegradora_android.ui_state

import com.example.farmacia_inegradora_android.models.RestockItem

data class RestockUIState(
    val isLoading: Boolean = false,
    val data: List<RestockItem> = emptyList(),
    val error: String = ""
)
