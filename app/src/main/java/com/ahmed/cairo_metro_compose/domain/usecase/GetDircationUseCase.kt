package com.ahmed.cairo_metro_compose.domain.usecase

import com.ahmed.cairo_metro_compose.domain.model.station

class GetDircationUseCase(
    private val getFirstStationUseCase: GetFirstStationUseCase,
    private val getLastStationUseCase: GeLastStationUseCase
) {
    operator fun invoke(
        current: station,
        next: station
    ): String {
        val firstStation = getFirstStationUseCase(current.line)
        val lastStation = getLastStationUseCase(current.line)
        return if (next.order > current.order) {
            lastStation
        } else {
            firstStation
        }
    }
}
