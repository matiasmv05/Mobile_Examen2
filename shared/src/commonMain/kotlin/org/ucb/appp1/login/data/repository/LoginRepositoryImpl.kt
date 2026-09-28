package org.ucb.appp1.login.data.repository

import kotlinx.coroutines.CancellationException
import org.ucb.appp1.login.domain.model.UserModel
import org.ucb.appp1.login.domain.repository.LoginRepository

class LoginRepositoryImpl : LoginRepository {
    override suspend fun login(username: String, password: String): Result<UserModel> =
        try {
            if (username.isBlank() || password.isBlank()) {
                Result.failure(IllegalArgumentException("Por favor ingrese usuario y contraseña"))
            } else {
                Result.success(UserModel(username = username, token = "dummy_token"))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
}
