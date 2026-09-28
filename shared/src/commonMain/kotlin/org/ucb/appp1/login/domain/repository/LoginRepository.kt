package org.ucb.appp1.login.domain.repository

import org.ucb.appp1.login.domain.model.UserModel

interface LoginRepository {
    suspend fun login(username: String, password: String): Result<UserModel>
}
