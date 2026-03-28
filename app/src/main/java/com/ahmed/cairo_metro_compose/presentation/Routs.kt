package com.ahmed.cairo_metro_compose.presentation

import kotlinx.serialization.Serializable

@Serializable
object HomeRouts

@Serializable
data class DetailsRoute(
    val start_station: String,
    val end_station: String,
)