package org.ucb.appp1.starwars.data.datasource.remote

import org.ucb.appp1.starwars.data.dto.CharacterListResponseDto

interface CharacterRemoteDataSource {
    suspend fun fetchCharacters(page: Int): CharacterListResponseDto
}
