package org.ucb.appp1.starwars.domain.repository

import org.ucb.appp1.starwars.domain.model.CharacterPageModel

interface CharacterRepository {
    suspend fun getCharacters(page: Int): Result<CharacterPageModel>
}
