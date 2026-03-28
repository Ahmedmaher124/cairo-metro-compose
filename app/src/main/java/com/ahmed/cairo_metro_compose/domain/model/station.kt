package com.ahmed.cairo_metro_compose.domain.model

data class station(
    val id: Int,
    val name: String,
    val line: lines,
    val order: Int,
    val is_transfer: Boolean,
    val transfer_lines: List<lines>
)
