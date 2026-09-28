package org.ucb.appp1.catalog.data.datasource.remote

import org.ucb.appp1.catalog.data.dto.MovieDto

interface CatalogRemoteDataSource {
    suspend fun fetchMovies(): List<MovieDto>
}
