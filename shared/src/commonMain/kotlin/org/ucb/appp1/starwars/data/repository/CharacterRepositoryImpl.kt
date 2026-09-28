package org.ucb.appp1.starwars.data.repository

import kotlinx.coroutines.CancellationException
import org.ucb.appp1.starwars.data.datasource.remote.CharacterRemoteDataSource
import org.ucb.appp1.starwars.data.mapper.toModel
import org.ucb.appp1.starwars.domain.model.CharacterPageModel
import org.ucb.appp1.starwars.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val remoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): Result<CharacterPageModel> =
        try {
            val response = remoteDataSource.fetchCharacters(page)
            val models = response.results.map { it.toModel() }
            val hasNext = !response.next.isNullOrEmpty()
            Result.success(
                CharacterPageModel(
                    characters = models,
                    hasNextPage = hasNext,
                    totalCount = response.count ?: models.size
                )
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
}
