package com.example.farmacia_inegradora_android.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmacia_inegradora_android.data.PharmacyRepository
import com.example.farmacia_inegradora_android.data.SessionManager
import com.example.farmacia_inegradora_android.ui_state.SalesUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SalesViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(SalesUIState())
    val uiState: StateFlow<SalesUIState> = _uiState.asStateFlow()

    private val repo = PharmacyRepository

    private val sessionManager = SessionManager(application)

    init {
        getSales()
    }

    fun getSales() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val token = sessionManager.getToken() ?: throw Exception("No hay sesión activa. Inicia sesión nuevamente.")

                val salesList = repo.getSales(token)

                _uiState.update {
                    it.copy(isLoading = false, allSales = salesList, filteredSales = salesList)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Error: ${e.message}") }
            }
        }
    }

    fun updateFilterType(type: String) {
        _uiState.update { it.copy(filterType = type) }
        applyFilters()
    }

    fun updateDates(start: String, end: String) {
        _uiState.update { it.copy(startDate = start, endDate = end) }
        applyFilters()
    }

    private fun applyFilters() {
        val state = _uiState.value
        var filtered = state.allSales

        if (state.filterType != "Todas") {
            filtered = filtered.filter { it.sale_type.equals(state.filterType, ignoreCase = true) }
        }

        if (state.startDate.isNotBlank()) {
            filtered = filtered.filter { it.date >= state.startDate }
        }
        if (state.endDate.isNotBlank()) {
            filtered = filtered.filter { it.date <= state.endDate }
        }

        _uiState.update { it.copy(filteredSales = filtered) }
    }
}