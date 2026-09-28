package org.ucb.appp1.catalog.domain.model

data class MovieModel(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val voteAverage: Double,
    val releaseDate: String
)
