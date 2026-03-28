package com.ahmed.cairo_metro_compose.domain.usecase

import com.ahmed.cairo_metro_compose.domain.model.lines
import com.ahmed.cairo_metro_compose.domain.repo.MetroRepo

class GeLastStationUseCase(private val repo: MetroRepo) {
    operator fun invoke(metroLine: lines): String {
        return repo.getStationList()
            .filter { it.line == metroLine }
            .maxByOrNull { it.order }
            ?.name ?: ""
    }
}
