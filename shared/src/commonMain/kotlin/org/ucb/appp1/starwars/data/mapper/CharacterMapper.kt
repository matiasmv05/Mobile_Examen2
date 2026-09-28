package org.ucb.appp1.starwars.data.mapper

import org.ucb.appp1.starwars.data.dto.CharacterDto
import org.ucb.appp1.starwars.domain.model.CharacterModel

fun CharacterDto.toModel(): CharacterModel =
    CharacterModel(
        name = name.orEmpty().ifBlank { "Desconocido" },
        height = height.orEmpty().ifBlank { "N/A" },
        mass = mass.orEmpty().ifBlank { "N/A" },
        hairColor = hairColor.orEmpty().ifBlank { "N/A" },
        skinColor = skinColor.orEmpty().ifBlank { "N/A" },
        eyeColor = eyeColor.orEmpty().ifBlank { "N/A" },
        gender = gender.orEmpty().ifBlank { "N/A" }
    )
