package org.ucb.appp1

import androidx.compose.runtime.Composable
import org.koin.compose.KoinContext
import org.ucb.appp1.core.navigation.AppNavHost
import org.ucb.appp1.core.theme.AppTheme

@Composable
fun App() {
    KoinContext {
        AppTheme {
            AppNavHost()
        }
    }
}
