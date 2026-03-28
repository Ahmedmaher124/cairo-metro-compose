package com.ahmed.cairo_metro_compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    primaryContainer = SurfaceDark,
    onPrimaryContainer = Color.White,
    background = BackgroundDark,
    surface = SurfaceDark,
    onSurface = Color.White,
    error = Color(0xFFCF6679)
)

private val LightColorScheme = lightColorScheme(
    primary = Line3Color,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE3F2FD),
    onPrimaryContainer = Line3Color,
    background = BackgroundLight,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFB00020)
)

@Composable
fun Cairo_Metro_ComposeTheme(
    isDarkMode: Boolean? = null,
    content: @Composable () -> Unit
) {
    val darkTheme = isDarkMode ?: isSystemInDarkTheme()
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
