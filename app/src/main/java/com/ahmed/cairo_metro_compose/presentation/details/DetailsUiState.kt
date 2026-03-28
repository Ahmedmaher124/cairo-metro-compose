package com.ahmed.cairo_metro_compose.presentation.details

import com.ahmed.cairo_metro_compose.domain.model.station

data class DetailsUiState(
    val startStation: String = "",
    val endStation: String = "",
    val path: List<station> = emptyList(),
    val fare: Int? = null,
    val time: Int? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val expandedStationIndex: Int? = null
)

