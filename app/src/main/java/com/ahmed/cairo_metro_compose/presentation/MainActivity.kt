package com.ahmed.cairo_metro_compose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ahmed.cairo_metro_compose.ui.theme.Cairo_Metro_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Cairo_Metro_ComposeTheme(isDarkMode = null) {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}
