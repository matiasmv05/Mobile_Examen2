package org.ucb.appp1.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PrimaryColor = Color(0xFF6200EE)
val PrimaryVariant = Color(0xFF3700B3)
val SecondaryColor = Color(0xFF03DAC6)

val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = Color(0xFFF5F5F5),
    surface = Color.White
)

val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E)
)

@Composable
expect fun appColorScheme(darkTheme: Boolean, dynamicColor: Boolean): ColorScheme

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = appColorScheme(darkTheme, dynamicColor),
        content = content
    )
}
