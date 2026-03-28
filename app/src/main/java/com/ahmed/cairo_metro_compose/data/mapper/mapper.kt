package com.ahmed.cairo_metro_compose.data.mapper

import com.ahmed.cairo_metro_compose.data.model.StationDto
import com.ahmed.cairo_metro_compose.domain.model.lines
import com.ahmed.cairo_metro_compose.domain.model.station

object Mapper {
    fun mapStationDtoToStation(stationDto: StationDto): station {
        return station(
            id = stationDto.id,
            name = stationDto.name,
            line = stationDto.line.toMetroMapLine(),
            order = stationDto.order,
            is_transfer = stationDto.is_transfer,
            transfer_lines = stationDto.transfer_lines.map { it.toMetroMapLine() }
        )
    }

    private fun String.toMetroMapLine() = when (this.trim().uppercase()) {
        "FIRST LINE" -> lines.Line1
        "SECOND LINE" -> lines.Line2
        "THIRD LINE" -> lines.Line3
        else -> lines.Line0
    }
}
