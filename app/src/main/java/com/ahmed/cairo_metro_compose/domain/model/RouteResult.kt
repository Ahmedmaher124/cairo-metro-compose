package com.ahmed.cairo_metro_compose.domain.model

sealed class RouteResult {
    data class Success(
        val path: List<station>,
        val fare: Int,
        val time: Int
    ) : RouteResult()

    data class Error(
        val message: String
    ) : RouteResult()
}
