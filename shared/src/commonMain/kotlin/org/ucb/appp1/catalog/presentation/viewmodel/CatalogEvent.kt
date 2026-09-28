package org.ucb.appp1.catalog.presentation.viewmodel

sealed interface CatalogEvent {
    data object OnLoad : CatalogEvent
    data object OnRetry : CatalogEvent
    data class OnShowDetail(val id: Int) : CatalogEvent
}
