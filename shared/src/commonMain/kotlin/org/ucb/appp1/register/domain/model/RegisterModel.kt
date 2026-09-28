package org.ucb.appp1.register.domain.model

data class RegisterModel(
    val fullName: String,
    val email: String,
    val token: String? = null
)
