package org.ucb.appp1.register.data.repository

import kotlinx.coroutines.CancellationException
import org.ucb.appp1.register.domain.model.RegisterModel
import org.ucb.appp1.register.domain.repository.RegisterRepository

class RegisterRepositoryImpl : RegisterRepository {
    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): Result<RegisterModel> =
        try {
            if (fullName.isBlank() || email.isBlank() || password.isBlank()) {
                Result.failure(IllegalArgumentException("Completa todos los campos"))
            } else {
                Result.success(RegisterModel(fullName = fullName, email = email, token = "dummy_token"))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
}
