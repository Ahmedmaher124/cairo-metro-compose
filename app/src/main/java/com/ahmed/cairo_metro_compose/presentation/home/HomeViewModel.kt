package com.ahmed.cairo_metro_compose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.cairo_metro_compose.domain.repo.MetroRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: MetroRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadStations()
    }

    private fun loadStations() {
        viewModelScope.launch {
            try {
                val stations = repo.getStationList().distinctBy { it.name }
                _uiState.update { it.copy(stations = stations, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        error = "Failed to load stations: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onStartStationSelected(stationName: String) {
        _uiState.update { 
            it.copy(
                startStation = stationName,
                isStartDropdownExpanded = false
            )
        }
    }

    fun onEndStationSelected(stationName: String) {
        _uiState.update { 
            it.copy(
                endStation = stationName,
                isEndDropdownExpanded = false
            )
        }
    }

    fun toggleStartDropdown() {
        _uiState.update { it.copy(isStartDropdownExpanded = !it.isStartDropdownExpanded) }
    }

    fun toggleEndDropdown() {
        _uiState.update { it.copy(isEndDropdownExpanded = !it.isEndDropdownExpanded) }
    }

    fun clearSelection() {
        _uiState.update {
            it.copy(
                startStation = "",
                endStation = "",
                error = null
            )
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

