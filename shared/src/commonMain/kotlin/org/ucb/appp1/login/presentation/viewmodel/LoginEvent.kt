package org.ucb.appp1.login.presentation.viewmodel

sealed interface LoginEvent {
    data class OnUsernameChanged(val username: String) : LoginEvent
    data class OnPasswordChanged(val password: String) : LoginEvent
    data object OnTogglePasswordVisibility : LoginEvent
    data object OnSubmitLogin : LoginEvent
    data object OnNavigateToRegister : LoginEvent
    data object OnForgotPasswordClicked : LoginEvent
}
