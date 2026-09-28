package org.ucb.appp1.starwars.presentation.viewmodel

import org.ucb.appp1.starwars.domain.model.CharacterModel

data class CharacterState(
    val isLoading: Boolean = false,
    val isMoreLoading: Boolean = false,
    val currentPage: Int = 1,
    val hasNextPage: Boolean = true,
    val totalCount: Int = 0,
    val error: String? = null,
    val characters: List<CharacterModel> = emptyList(),
    val selectedCharacter: CharacterModel? = null
)
