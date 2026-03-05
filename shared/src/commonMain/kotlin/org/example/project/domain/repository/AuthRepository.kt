package org.example.project.domain.repository

import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.api.AuthApi

class AuthRepository(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage
) {
    suspend fun login(microsoftAccessToken: String): Result<Unit> {
        try {
            val response = authApi.login(microsoftAccessToken)
            val token = response.data?.token ?: return Result.failure(Exception("$response"))
            tokenStorage.saveAccessToken(token)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private fun logout() {}
}