package data.model

import com.ahmed.cairo_metro_compose.data.model.StationDto

data class MetroDto(
    val stations: List<StationDto>,
    val travel_time_between_stations_minutes: Int
)
