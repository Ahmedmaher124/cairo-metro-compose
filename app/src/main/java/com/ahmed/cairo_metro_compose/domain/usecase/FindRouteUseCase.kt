package com.ahmed.cairo_metro_compose.domain.usecase

import com.ahmed.cairo_metro_compose.domain.model.RouteResult
import com.ahmed.cairo_metro_compose.domain.repo.MetroRepo

class FindRouteUseCase(
    private val repo: MetroRepo,
    private val calculateFareUseCase: CalculateFairUseCase,
    private val calculateTimeUseCase: CalculateTimeUseCase,
    private val bfsUseCase: BFSUseCase
) {

    operator fun invoke(startName: String, endName: String): RouteResult {
        val stations = repo.getStationList()

        if (stations.isEmpty()) {
            return RouteResult.Error("No stations available")
        }

        val stationMap = stations.associateBy { it.name.trim().lowercase() }

        val start = stationMap[startName.trim().lowercase()]
            ?: return RouteResult.Error("Start station not found")

        val end = stationMap[endName.trim().lowercase()]
            ?: return RouteResult.Error("End station not found")

        if (start == end) {
            return RouteResult.Success(
                path = listOf(start),
                fare = 0,
                time = 0
            )
        }

        val path = bfsUseCase(start, end, stations)
            ?: return RouteResult.Error("Path not found")

        val stationCount = path.size
        val fare = calculateFareUseCase(stationCount)
        val time = calculateTimeUseCase(stationCount)

        return RouteResult.Success(
            path = path,
            fare = fare,
            time = time
        )
    }
}
