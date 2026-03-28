package com.ahmed.cairo_metro_compose.presentation.home

import com.ahmed.cairo_metro_compose.domain.model.station

data class HomeUiState(
    val stations: List<station> = emptyList(),
    val startStation: String = "",
    val endStation: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val isStartDropdownExpanded: Boolean = false,
    val isEndDropdownExpanded: Boolean = false
)

