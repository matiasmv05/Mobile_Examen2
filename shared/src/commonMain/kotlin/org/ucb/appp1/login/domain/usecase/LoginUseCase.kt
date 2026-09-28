package org.ucb.appp1.login.domain.usecase

import org.ucb.appp1.login.domain.model.UserModel
import org.ucb.appp1.login.domain.repository.LoginRepository

class LoginUseCase(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<UserModel> =
        repository.login(username, password)
}
