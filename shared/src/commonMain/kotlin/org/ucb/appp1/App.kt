package org.ucb.appp1

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.koin.compose.KoinContext
import org.ucb.appp1.core.navigation.AppNavHost

@Composable
fun App() {
    KoinContext {
        MaterialTheme {
            AppNavHost()
        }
    }
}
