package org.ucb.appp1.catalog.data.repository

import kotlinx.coroutines.CancellationException
import org.ucb.appp1.catalog.data.datasource.remote.CatalogRemoteDataSource
import org.ucb.appp1.catalog.data.mapper.toModel
import org.ucb.appp1.catalog.domain.model.MovieModel
import org.ucb.appp1.catalog.domain.repository.CatalogRepository

class CatalogRepositoryImpl(
    private val remoteDataSource: CatalogRemoteDataSource
) : CatalogRepository {

    override suspend fun getMovies(): Result<List<MovieModel>> =
        try {
            Result.success(remoteDataSource.fetchMovies().map { it.toModel() })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
}
