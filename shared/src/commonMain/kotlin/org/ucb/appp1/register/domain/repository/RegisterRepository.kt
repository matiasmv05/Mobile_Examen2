package org.ucb.appp1.register.domain.repository

import org.ucb.appp1.register.domain.model.RegisterModel

interface RegisterRepository {
    suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): Result<RegisterModel>
}
