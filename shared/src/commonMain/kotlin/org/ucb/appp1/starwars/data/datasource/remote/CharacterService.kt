package org.ucb.appp1.starwars.data.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.ucb.appp1.starwars.data.dto.CharacterListResponseDto

class CharacterService(
    private val client: HttpClient
) : CharacterRemoteDataSource {

    override suspend fun fetchCharacters(page: Int): CharacterListResponseDto =
        client.get("https://swapi.dev/api/people/") {
            parameter("page", page)
        }.body()
}
