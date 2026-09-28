package org.ucb.appp1.catalog.data.mapper

import org.ucb.appp1.catalog.data.dto.MovieDto
import org.ucb.appp1.catalog.domain.model.MovieModel

fun MovieDto.toModel(): MovieModel {
    val fullPosterUrl = posterPath?.let { path ->
        if (path.startsWith("http")) path else "https://image.tmdb.org/t/p/w500$path"
    }
    return MovieModel(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl = fullPosterUrl,
        voteAverage = voteAverage ?: 0.0,
        releaseDate = releaseDate.orEmpty()
    )
}
