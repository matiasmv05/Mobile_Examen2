package org.ucb.appp1.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.ucb.appp1.catalog.presentation.screen.CatalogScreen
import org.ucb.appp1.login.presentation.screen.LoginScreen
import org.ucb.appp1.register.presentation.screen.RegisterScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Login
    ) {
        composable<NavRoute.Login> {
            LoginScreen(
                onNavigateToCatalog = {
                    navController.navigate(NavRoute.Catalog) {
                        popUpTo<NavRoute.Login> { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(NavRoute.Register)
                }
            )
        }

        composable<NavRoute.Register> {
            RegisterScreen(
                onNavigateToCatalog = {
                    navController.navigate(NavRoute.Catalog) {
                        popUpTo<NavRoute.Login> { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable<NavRoute.Catalog> {
            CatalogScreen(
                onNavigateToDetail = { movieId ->
                    navController.navigate(NavRoute.MovieDetail(movieId))
                }
            )
        }

        composable<NavRoute.MovieDetail> {
            // Movie detail route placeholder
        }
    }
}
