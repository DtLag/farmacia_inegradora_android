package com.example.farmacia_inegradora_android.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmacia_inegradora_android.data.RetrofitInstance
import com.example.farmacia_inegradora_android.ui_state.RestockUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RestockViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RestockUIState(isLoading = true))
    val uiState: StateFlow<RestockUIState> = _uiState

    init {
        fetchRestockProjection()
    }

    fun fetchRestockProjection() {
        viewModelScope.launch {
            _uiState.value = RestockUIState(isLoading = true)

            try {
                val response = RetrofitInstance.API.getStockRecommendations()

                _uiState.value = RestockUIState(
                    isLoading = false,
                    data = response.data,
                    error = ""
                )

            } catch (e: Exception) {
                _uiState.value = RestockUIState(
                    isLoading = false,
                    data = emptyList(),
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }
}