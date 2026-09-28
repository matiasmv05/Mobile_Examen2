package org.ucb.appp1.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.ucb.appp1.starwars.presentation.screen.CharacterScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.StarWars
    ) {
        composable<NavRoute.StarWars> {
            CharacterScreen(
                onBack = {}
            )
        }
    }
}
