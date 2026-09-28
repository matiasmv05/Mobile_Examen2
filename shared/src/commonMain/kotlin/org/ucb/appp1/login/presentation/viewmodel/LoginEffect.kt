package org.ucb.appp1.login.presentation.viewmodel

sealed interface LoginEffect {
    data object NavigateToCatalog : LoginEffect
    data object NavigateToRegister : LoginEffect
    data class ShowMessage(val message: String) : LoginEffect
}
