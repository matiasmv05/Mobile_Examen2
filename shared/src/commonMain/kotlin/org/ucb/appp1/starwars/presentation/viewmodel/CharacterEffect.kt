package org.ucb.appp1.starwars.presentation.viewmodel

sealed interface CharacterEffect {
    data class ShowMessage(val message: String) : CharacterEffect
}
