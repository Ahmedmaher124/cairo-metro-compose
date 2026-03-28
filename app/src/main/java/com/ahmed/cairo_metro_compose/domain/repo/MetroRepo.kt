package com.ahmed.cairo_metro_compose.domain.repo

import com.ahmed.cairo_metro_compose.domain.model.station

interface MetroRepo {
    fun getStationList(): List<station>
    fun getTravelTimeBetweenStationsMinutes(): Int
}
