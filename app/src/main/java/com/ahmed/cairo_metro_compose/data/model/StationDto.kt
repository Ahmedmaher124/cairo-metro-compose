package com.ahmed.cairo_metro_compose.data.model

data class StationDto(
    val id: Int,
    val name: String,
    val line: String,
    val order: Int,
    val is_transfer: Boolean,
    val transfer_lines: List<String>
)
