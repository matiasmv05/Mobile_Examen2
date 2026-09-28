package org.ucb.appp1.login.domain.model

data class UserModel(
    val username: String,
    val token: String? = null
)
