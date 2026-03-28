package com.ahmed.cairo_metro_compose.data.RepoImpl

import com.ahmed.cairo_metro_compose.data.datasource.MetroDataSource
import com.ahmed.cairo_metro_compose.data.mapper.Mapper
import com.ahmed.cairo_metro_compose.domain.model.station
import com.ahmed.cairo_metro_compose.domain.repo.MetroRepo

class RepoImpl(private val metroDataSource: MetroDataSource) : MetroRepo {
    override fun getStationList(): List<station> {
        return metroDataSource.loadStationList().map { Mapper.mapStationDtoToStation(it) }
    }

    override fun getTravelTimeBetweenStationsMinutes(): Int {
        return metroDataSource.getTravelTimeBetweenStationsMinutes()
    }
}
