package org.ucb.appp1.register.presentation.viewmodel

sealed interface RegisterEffect {
    data object NavigateToCatalog : RegisterEffect
    data object NavigateToLogin : RegisterEffect
    data class ShowMessage(val message: String) : RegisterEffect
}
