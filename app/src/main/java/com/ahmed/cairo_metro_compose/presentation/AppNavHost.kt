package com.ahmed.cairo_metro_compose.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.ahmed.cairo_metro_compose.presentation.details.DetailsScreen
import com.ahmed.cairo_metro_compose.presentation.details.DetailsViewModel
import com.ahmed.cairo_metro_compose.presentation.details.DetailsViewModelFactory
import com.ahmed.cairo_metro_compose.presentation.home.HomeScreen
import com.ahmed.cairo_metro_compose.presentation.home.HomeViewModel
import com.ahmed.cairo_metro_compose.presentation.home.HomeViewModelFactory

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    val context = LocalContext.current
    
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(context)
            )
            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToDetails = { start, end ->
                    navController.navigate("details/$start/$end")
                }
            )
        }
        composable("details/{start}/{end}") { backStackEntry ->
            val start = backStackEntry.arguments?.getString("start") ?: ""
            val end = backStackEntry.arguments?.getString("end") ?: ""
            
            val detailsViewModel: DetailsViewModel = viewModel(
                factory = DetailsViewModelFactory(context)
            )
            
            LaunchedEffect(start, end) {
                detailsViewModel.findRoute(start, end)
            }
            
            DetailsScreen(
                viewModel = detailsViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate("home", navOptions {
                        popUpTo("home") { inclusive = true }
                    })
                }
            )
        }
    }
}
