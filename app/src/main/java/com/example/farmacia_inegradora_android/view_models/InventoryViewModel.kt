package com.example.farmacia_inegradora_android.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmacia_inegradora_android.data.PharmacyRepository
import com.example.farmacia_inegradora_android.ui_state.InventoryUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InventoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InventoryUIState())
    val uiState: StateFlow<InventoryUIState> = _uiState.asStateFlow()

    private val repo = PharmacyRepository

    init {
        getInventory()
    }

    fun getInventory() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val products = repo.getInventory()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        productsList = products,
                        filteredProductsList = products
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Error de red: ${e.message}") }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        val currentList = _uiState.value.productsList

        val filtered = if (query.isBlank()) {
            currentList
        } else {
            currentList.filter { product ->
                product.name.contains(query, ignoreCase = true) ||
                        product.codigo.contains(query, ignoreCase = true)
            }
        }

        _uiState.update {
            it.copy(searchQuery = query, filteredProductsList = filtered)
        }
    }
}