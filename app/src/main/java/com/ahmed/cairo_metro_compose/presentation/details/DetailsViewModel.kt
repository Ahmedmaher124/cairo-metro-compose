package com.ahmed.cairo_metro_compose.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.cairo_metro_compose.domain.model.RouteResult
import com.ahmed.cairo_metro_compose.domain.usecase.FindRouteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val findRouteUseCase: FindRouteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun findRoute(startStation: String, endStation: String) {
        if (startStation.isBlank() || endStation.isBlank()) {
            _uiState.update { 
                it.copy(error = "Please select both stations")
            }
            return
        }

        _uiState.update { 
            it.copy(
                startStation = startStation,
                endStation = endStation,
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            when (val result = findRouteUseCase(startStation, endStation)) {
                is RouteResult.Success -> {
                    _uiState.update {
                        it.copy(
                            path = result.path,
                            fare = result.fare,
                            time = result.time,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is RouteResult.Error -> {
                    _uiState.update {
                        it.copy(
                            error = result.message,
                            isLoading = false,
                            path = emptyList(),
                            fare = null,
                            time = null
                        )
                    }
                }
            }
        }
    }

    fun toggleStationExpanded(index: Int) {
        _uiState.update {
            it.copy(
                expandedStationIndex = if (it.expandedStationIndex == index) null else index
            )
        }
    }

    fun clearRoute() {
        _uiState.update { 
            DetailsUiState()
        }
    }
}
