package com.ahmed.cairo_metro_compose.data.datasource

import com.ahmed.cairo_metro_compose.data.model.StationDto

interface MetroDataSource {
    fun loadStationList(): List<StationDto>
    fun getTravelTimeBetweenStationsMinutes(): Int
}
