package org.ucb.appp1.register.presentation.viewmodel

sealed interface RegisterEvent {
    data class OnFullNameChanged(val fullName: String) : RegisterEvent
    data class OnEmailChanged(val email: String) : RegisterEvent
    data class OnPasswordChanged(val password: String) : RegisterEvent
    data class OnConfirmPasswordChanged(val confirmPassword: String) : RegisterEvent
    data object OnTogglePasswordVisibility : RegisterEvent
    data object OnSubmitRegister : RegisterEvent
    data object OnNavigateToLogin : RegisterEvent
}
