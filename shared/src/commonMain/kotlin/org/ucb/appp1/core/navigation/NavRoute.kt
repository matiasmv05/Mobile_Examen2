package org.ucb.appp1.core.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoute {
    @Serializable
    data object StarWars : NavRoute
}
