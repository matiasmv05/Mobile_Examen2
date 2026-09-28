package org.ucb.appp1.starwars.presentation.viewmodel

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
import org.ucb.appp1.starwars.domain.usecase.GetCharactersUseCase

class CharacterViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterState())
    val state: StateFlow<CharacterState> = _state.asStateFlow()

    private val _effect = Channel<CharacterEffect>(Channel.BUFFERED)
    val effect: Flow<CharacterEffect> = _effect.receiveAsFlow()

    fun emitEvent(event: CharacterEvent) {
        when (event) {
            CharacterEvent.OnLoad -> {
                if (_state.value.characters.isEmpty() && !_state.value.isLoading) {
                    loadPage(page = 1, isNextPage = false)
                }
            }
            CharacterEvent.OnLoadNextPage -> {
                val current = _state.value
                if (current.hasNextPage && !current.isLoading && !current.isMoreLoading) {
                    loadPage(page = current.currentPage + 1, isNextPage = true)
                }
            }
            CharacterEvent.OnRetry -> loadPage(
                page = _state.value.currentPage,
                isNextPage = _state.value.characters.isNotEmpty()
            )
            is CharacterEvent.OnSelectCharacter -> _state.update { it.copy(selectedCharacter = event.character) }
            CharacterEvent.OnDismissDetailDialog -> _state.update { it.copy(selectedCharacter = null) }
        }
    }

    private fun loadPage(page: Int, isNextPage: Boolean) {
        viewModelScope.launch {
            if (isNextPage) {
                _state.update { it.copy(isMoreLoading = true) }
            } else {
                _state.update { it.copy(isLoading = true, error = null) }
            }

            getCharactersUseCase(page)
                .onSuccess { pageModel ->
                    _state.update { current ->
                        val updatedList = if (isNextPage) current.characters + pageModel.characters else pageModel.characters
                        current.copy(
                            isLoading = false,
                            isMoreLoading = false,
                            currentPage = page,
                            hasNextPage = pageModel.hasNextPage,
                            totalCount = pageModel.totalCount,
                            characters = updatedList
                        )
                    }
                }
                .onFailure { error ->
                    val msg = "Error al cargar la página $page de personajes"
                    _state.update { current ->
                        current.copy(
                            isLoading = false,
                            isMoreLoading = false,
                            error = if (current.characters.isEmpty()) msg else null
                        )
                    }
                    emitEffect(CharacterEffect.ShowMessage(msg))
                }
        }
    }

    private fun emitEffect(effect: CharacterEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
