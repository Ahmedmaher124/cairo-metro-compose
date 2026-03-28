package com.ahmed.cairo_metro_compose.data.datasource

import android.content.Context
import androidx.annotation.RawRes

import com.ahmed.cairo_metro_compose.data.model.StationDto
import com.google.gson.Gson
import data.model.MetroDto

class MetroJsonDataSource(private val context: Context, @RawRes private val resId: Int) : MetroDataSource {
    private val gson = Gson()
    
    private val dto: MetroDto by lazy {
        val jsonString = context.resources.openRawResource(resId).bufferedReader().use { it.readText() }
        gson.fromJson(jsonString, MetroDto::class.java)
    }

    override fun loadStationList(): List<StationDto> = dto.stations
    
    override fun getTravelTimeBetweenStationsMinutes(): Int = dto.travel_time_between_stations_minutes
}
