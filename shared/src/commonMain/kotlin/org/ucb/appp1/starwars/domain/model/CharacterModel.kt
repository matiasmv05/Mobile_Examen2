package org.ucb.appp1.starwars.domain.model

data class CharacterModel(
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val gender: String
)

data class CharacterPageModel(
    val characters: List<CharacterModel>,
    val hasNextPage: Boolean,
    val totalCount: Int
)
