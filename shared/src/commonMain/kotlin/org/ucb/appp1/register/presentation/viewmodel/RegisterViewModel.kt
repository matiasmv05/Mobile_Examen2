package org.ucb.appp1.register.presentation.viewmodel

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
import org.ucb.appp1.register.domain.usecase.RegisterUseCase

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    private val _effect = Channel<RegisterEffect>(Channel.BUFFERED)
    val effect: Flow<RegisterEffect> = _effect.receiveAsFlow()

    fun emitEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnFullNameChanged -> _state.update { it.copy(fullName = event.fullName, errorMessage = null) }
            is RegisterEvent.OnEmailChanged -> _state.update { it.copy(email = event.email, errorMessage = null) }
            is RegisterEvent.OnPasswordChanged -> _state.update { it.copy(password = event.password, errorMessage = null) }
            is RegisterEvent.OnConfirmPasswordChanged -> _state.update { it.copy(confirmPassword = event.confirmPassword, errorMessage = null) }
            RegisterEvent.OnTogglePasswordVisibility -> _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            RegisterEvent.OnSubmitRegister -> performRegister()
            RegisterEvent.OnNavigateToLogin -> emitEffect(RegisterEffect.NavigateToLogin)
        }
    }

    private fun performRegister() {
        val current = _state.value
        if (current.fullName.isBlank() || current.email.isBlank() || current.password.isBlank() || current.confirmPassword.isBlank()) {
            val msg = "Completa todos los campos"
            _state.update { it.copy(errorMessage = msg) }
            emitEffect(RegisterEffect.ShowMessage(msg))
            return
        }

        if (current.password != current.confirmPassword) {
            val msg = "Las contraseñas no coinciden"
            _state.update { it.copy(errorMessage = msg) }
            emitEffect(RegisterEffect.ShowMessage(msg))
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            registerUseCase(current.fullName, current.email, current.password)
                .onSuccess {
                    _state.update { state -> state.copy(isLoading = false) }
                    emitEffect(RegisterEffect.NavigateToCatalog)
                }
                .onFailure { error ->
                    val msg = error.message ?: "Error al registrar la cuenta"
                    _state.update { state -> state.copy(isLoading = false, errorMessage = msg) }
                    emitEffect(RegisterEffect.ShowMessage(msg))
                }
        }
    }

    private fun emitEffect(effect: RegisterEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
