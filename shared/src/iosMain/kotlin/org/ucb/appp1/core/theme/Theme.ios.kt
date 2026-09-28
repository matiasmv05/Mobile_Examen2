package org.ucb.appp1.core.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun appColorScheme(darkTheme: Boolean, dynamicColor: Boolean): ColorScheme =
    if (darkTheme) DarkColorScheme else LightColorScheme
