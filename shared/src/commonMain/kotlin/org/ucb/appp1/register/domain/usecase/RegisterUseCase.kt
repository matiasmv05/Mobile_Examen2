package org.ucb.appp1.register.domain.usecase

import org.ucb.appp1.register.domain.model.RegisterModel
import org.ucb.appp1.register.domain.repository.RegisterRepository

class RegisterUseCase(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke(
        fullName: String,
        email: String,
        password: String
    ): Result<RegisterModel> = repository.register(fullName, email, password)
}
