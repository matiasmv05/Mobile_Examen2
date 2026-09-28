package org.ucb.appp1.login.presentation.viewmodel

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
import org.ucb.appp1.login.domain.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _effect = Channel<LoginEffect>(Channel.BUFFERED)
    val effect: Flow<LoginEffect> = _effect.receiveAsFlow()

    fun emitEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnUsernameChanged -> _state.update { it.copy(username = event.username, errorMessage = null) }
            is LoginEvent.OnPasswordChanged -> _state.update { it.copy(password = event.password, errorMessage = null) }
            LoginEvent.OnTogglePasswordVisibility -> _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            LoginEvent.OnSubmitLogin -> performLogin()
            LoginEvent.OnNavigateToRegister -> emitEffect(LoginEffect.NavigateToRegister)
            LoginEvent.OnForgotPasswordClicked -> emitEffect(LoginEffect.ShowMessage("Recuperación de contraseña enviada"))
        }
    }

    private fun performLogin() {
        val current = _state.value
        if (current.username.isBlank() || current.password.isBlank()) {
            val msg = "Completa todos los campos"
            _state.update { it.copy(errorMessage = msg) }
            emitEffect(LoginEffect.ShowMessage(msg))
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            loginUseCase(current.username, current.password)
                .onSuccess {
                    _state.update { state -> state.copy(isLoading = false) }
                    emitEffect(LoginEffect.NavigateToCatalog)
                }
                .onFailure { error ->
                    val msg = error.message ?: "Error al iniciar sesión"
                    _state.update { state -> state.copy(isLoading = false, errorMessage = msg) }
                    emitEffect(LoginEffect.ShowMessage(msg))
                }
        }
    }

    private fun emitEffect(effect: LoginEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
