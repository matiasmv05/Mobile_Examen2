package org.ucb.appp1.catalog.domain

import kotlinx.coroutines.test.runTest
import org.ucb.appp1.catalog.domain.model.MovieModel
import org.ucb.appp1.catalog.domain.repository.CatalogRepository
import org.ucb.appp1.catalog.domain.usecase.GetMoviesUseCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetMoviesUseCaseTest {

    private class FakeCatalogRepository(
        private val result: Result<List<MovieModel>>
    ) : CatalogRepository {
        override suspend fun getMovies(): Result<List<MovieModel>> = result
    }

    @Test
    fun invoke_returnsSuccessFromRepository() = runTest {
        val movies = listOf(
            MovieModel(
                id = 1,
                title = "Super Mario Bros",
                overview = "A movie about Mario",
                posterUrl = "https://image.tmdb.org/t/p/w500/mario.jpg",
                voteAverage = 8.5,
                releaseDate = "2023-04-05"
            )
        )
        val repository = FakeCatalogRepository(Result.success(movies))
        val useCase = GetMoviesUseCase(repository)

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals(movies, result.getOrNull())
    }

    @Test
    fun invoke_returnsFailureFromRepository() = runTest {
        val error = RuntimeException("Network error")
        val repository = FakeCatalogRepository(Result.failure(error))
        val useCase = GetMoviesUseCase(repository)

        val result = useCase()

        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
}
