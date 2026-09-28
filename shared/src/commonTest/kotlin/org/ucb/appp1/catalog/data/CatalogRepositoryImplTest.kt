package org.ucb.appp1.catalog.data

import org.ucb.appp1.catalog.data.datasource.remote.CatalogRemoteDataSource
import org.ucb.appp1.catalog.data.dto.MovieDto
import org.ucb.appp1.catalog.data.repository.CatalogRepositoryImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CatalogRepositoryImplTest {

    private class FakeRemoteDataSource(
        private val dtos: List<MovieDto>,
        private val shouldFail: Boolean = false
    ) : CatalogRemoteDataSource {
        override suspend fun fetchMovies(): List<MovieDto> {
            if (shouldFail) throw RuntimeException("API error")
            return dtos
        }
    }
}
