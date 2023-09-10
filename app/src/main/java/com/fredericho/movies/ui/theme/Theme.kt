package com.fredericho.movies.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = BlackPrimary,
    background = Navy,
    surface = Navy.copy(alpha = 0.5f),
)

private val LightColorScheme = lightColorScheme(
    primary = BlackPrimary,
    background = Navy,
)

@Composable
fun MoviesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
   val colorScheme = if(darkTheme) {
       DarkColorScheme
   } else {
       LightColorScheme
   }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}