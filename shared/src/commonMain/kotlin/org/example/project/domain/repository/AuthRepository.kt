package org.example.project.domain.repository

import org.example.project.data.local.FirebaseStorage
import org.example.project.data.local.ImageBase64Storage
import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.api.AuthApi
import org.example.project.domain.model.AppResult

class AuthRepository(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage,
    private val imageStorage: ImageBase64Storage,
    private val firebaseStorage: FirebaseStorage
) {
    suspend fun login(microsoftAccessToken: String, deviceId: String): AppResult<Unit> {
        return try {
            val firebaseToken = firebaseStorage.getFirebaseToken()
                ?: return AppResult.Failure(message = "Firebase token not found")
            val response = authApi.login(microsoftAccessToken, firebaseToken, deviceId)
            val token = response.data?.accessToken
                ?: return AppResult.Failure(message = response.message)
            val refreshToken = response.data.refreshToken
            val imageBase64 = response.data.avatar

            tokenStorage.saveAccessToken(token)
            tokenStorage.saveRefreshToken(refreshToken)
            imageStorage.saveImageBase64(imageBase64)
            AppResult.Success(Unit)

        } catch (e: Exception) {
            AppResult.Failure(message = e.message)
        }
    }

    suspend fun signOut() {
        tokenStorage.clearAccessToken()
        tokenStorage.clearRefreshToken()
        imageStorage.clearImageBase64()
        firebaseStorage.clearAllTopics()
    }
}