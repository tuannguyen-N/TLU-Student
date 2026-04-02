package org.example.project.domain.repository

import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.api.AuthApi
import org.example.project.domain.model.AppResult

class AuthRepository(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage,
) {
    suspend fun login(microsoftAccessToken: String): AppResult<Unit> {
        return try {
            val response = authApi.login(microsoftAccessToken)
            val token = response.data?.token
                ?: return AppResult.Failure(message = response.message)

            tokenStorage.saveAccessToken(token)
            AppResult.Success(Unit)

        } catch (e: Exception) {
            AppResult.Failure(message = e.message)
        }
    }

    suspend fun signOut() {
        tokenStorage.clearAccessToken()
    }
}