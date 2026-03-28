package com.ahmed.cairo_metro_compose.domain.usecase

import com.ahmed.cairo_metro_compose.domain.repo.MetroRepo

class CalculateTimeUseCase(private val metroRepo: MetroRepo) {
    operator fun invoke(count: Int) = count * metroRepo.getTravelTimeBetweenStationsMinutes()
}
