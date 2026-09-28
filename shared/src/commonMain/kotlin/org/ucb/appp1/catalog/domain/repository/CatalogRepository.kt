package org.ucb.appp1.catalog.domain.repository

import org.ucb.appp1.catalog.domain.model.MovieModel

interface CatalogRepository {
    suspend fun getMovies(): Result<List<MovieModel>>
}
