package org.ucb.appp1.catalog.presentation.viewmodel

import org.ucb.appp1.catalog.domain.model.MovieModel

data class CatalogState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val movies: List<MovieModel> = emptyList()
)
