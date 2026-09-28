package org.ucb.appp1.catalog.domain.usecase

import org.ucb.appp1.catalog.domain.model.MovieModel
import org.ucb.appp1.catalog.domain.repository.CatalogRepository

class GetMoviesUseCase(
    private val repository: CatalogRepository
) {
    suspend operator fun invoke(): Result<List<MovieModel>> = repository.getMovies()
}
