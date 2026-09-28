package org.ucb.appp1.starwars.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterListResponseDto(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<CharacterDto> = emptyList()
)

@Serializable
data class CharacterDto(
    val name: String? = null,
    val height: String? = null,
    val mass: String? = null,
    @SerialName("hair_color") val hairColor: String? = null,
    @SerialName("skin_color") val skinColor: String? = null,
    @SerialName("eye_color") val eyeColor: String? = null,
    val gender: String? = null,
    val homeworld: String? = null,
    val url: String? = null
)
