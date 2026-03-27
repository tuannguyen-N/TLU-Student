package org.example.project.domain.repository

import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.api.AuthApi
import org.example.project.domain.model.ApiResult

class AuthRepository(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage,
) {
    suspend fun login(microsoftAccessToken: String): ApiResult<Unit> {
        return try {
            val response = authApi.login(microsoftAccessToken)
            val token = response.data?.token
                ?: return ApiResult.Failure(message = response.message)

            tokenStorage.saveAccessToken(token)
            ApiResult.Success(Unit)

        } catch (e: Exception) {
            ApiResult.Failure(message = e.message)
        }
    }

    suspend fun signOut() {
        tokenStorage.clearAccessToken()
    }
}