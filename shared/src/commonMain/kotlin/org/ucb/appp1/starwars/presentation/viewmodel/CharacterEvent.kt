package org.ucb.appp1.starwars.presentation.viewmodel

import org.ucb.appp1.starwars.domain.model.CharacterModel

sealed interface CharacterEvent {
    data object OnLoad : CharacterEvent
    data object OnLoadNextPage : CharacterEvent
    data object OnRetry : CharacterEvent
    data class OnSelectCharacter(val character: CharacterModel) : CharacterEvent
    data object OnDismissDetailDialog : CharacterEvent
}
