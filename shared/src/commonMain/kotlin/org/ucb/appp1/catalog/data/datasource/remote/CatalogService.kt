package org.ucb.appp1.catalog.data.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.ucb.appp1.catalog.data.dto.MovieDto
import org.ucb.appp1.catalog.data.dto.MovieListResponseDto

data class ApiConfig(
    val baseUrl: String = "https://api.themoviedb.org/3/",
    val apiKey: String = "fa3e844ce31744388e07fa47c7c5d8c3",
    val enableHttpLogging: Boolean = true
)

class CatalogService(
    private val client: HttpClient,
    private val config: ApiConfig
) : CatalogRemoteDataSource {

    override suspend fun fetchMovies(): List<MovieDto> {
        val response: MovieListResponseDto = client.get("discover/movie") {
            parameter("sort_by", "popularity.desc")
            parameter("api_key", config.apiKey)
        }.body()
        return response.results
    }
}
