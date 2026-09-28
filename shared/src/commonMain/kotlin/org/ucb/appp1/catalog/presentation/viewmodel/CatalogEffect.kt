package org.ucb.appp1.catalog.presentation.viewmodel

sealed interface CatalogEffect {
    data class ShowMessage(val message: String) : CatalogEffect
    data class NavigateToDetail(val id: Int) : CatalogEffect
}
