package org.ucb.appp1.catalog.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.catalog.domain.usecase.GetMoviesUseCase

class CatalogViewModel(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CatalogState())
    val state: StateFlow<CatalogState> = _state.asStateFlow()

    private val _effect = Channel<CatalogEffect>(Channel.BUFFERED)
    val effect: Flow<CatalogEffect> = _effect.receiveAsFlow()

    fun emitEvent(event: CatalogEvent) {
        when (event) {
            CatalogEvent.OnLoad -> {
                if (_state.value.movies.isEmpty() && !_state.value.isLoading) {
                    loadMovies()
                }
            }
            CatalogEvent.OnRetry -> loadMovies()
            is CatalogEvent.OnShowDetail -> emitEffect(CatalogEffect.NavigateToDetail(event.id))
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getMoviesUseCase()
                .onSuccess { movies ->
                    _state.update { it.copy(isLoading = false, movies = movies) }
                }
                .onFailure {
                    val message = "Error al cargar las películas"
                    _state.update { it.copy(isLoading = false, error = message) }
                    emitEffect(CatalogEffect.ShowMessage(message))
                }
        }
    }

    private fun emitEffect(effect: CatalogEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
