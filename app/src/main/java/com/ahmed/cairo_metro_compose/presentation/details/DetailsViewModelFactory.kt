package com.ahmed.cairo_metro_compose.presentation.details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmed.cairo_metro_compose.R
import com.ahmed.cairo_metro_compose.data.RepoImpl.RepoImpl
import com.ahmed.cairo_metro_compose.data.datasource.MetroJsonDataSource
import com.ahmed.cairo_metro_compose.domain.usecase.BFSUseCase
import com.ahmed.cairo_metro_compose.domain.usecase.CalculateFairUseCase
import com.ahmed.cairo_metro_compose.domain.usecase.CalculateTimeUseCase
import com.ahmed.cairo_metro_compose.domain.usecase.FindRouteUseCase

class DetailsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            val dataSource = MetroJsonDataSource(context, R.raw.cairo_metro_structured)
            val repo = RepoImpl(dataSource)
            val findRouteUseCase = FindRouteUseCase(
                repo = repo,
                calculateFareUseCase = CalculateFairUseCase(),
                calculateTimeUseCase = CalculateTimeUseCase(repo),
                bfsUseCase = BFSUseCase()
            )
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(findRouteUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
