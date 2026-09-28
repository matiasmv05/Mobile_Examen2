package org.ucb.appp1.core.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoute {
    @Serializable
    data object Login : NavRoute

    @Serializable
    data object Register : NavRoute

    @Serializable
    data object Catalog : NavRoute

    @Serializable
    data class MovieDetail(val id: Int) : NavRoute
}
