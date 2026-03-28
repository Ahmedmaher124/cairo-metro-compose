package com.ahmed.cairo_metro_compose.presentation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmed.cairo_metro_compose.R
import com.ahmed.cairo_metro_compose.data.RepoImpl.RepoImpl
import com.ahmed.cairo_metro_compose.data.datasource.MetroJsonDataSource

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val dataSource = MetroJsonDataSource(context, R.raw.cairo_metro_structured)
            val repo = RepoImpl(dataSource)
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

