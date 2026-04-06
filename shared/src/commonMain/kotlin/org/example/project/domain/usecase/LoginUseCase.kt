package org.example.project.domain.usecase

import org.example.project.data.local.FirebaseStorage
import org.example.project.domain.model.AppResult
import org.example.project.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository,
    private val firebaseStorage: FirebaseStorage
) {
    suspend operator fun invoke(microsoftAccessToken: String, deviceId: String): AppResult<Unit> {
        val firebaseToken = firebaseStorage.getFirebaseToken()
            ?: return AppResult.Failure("Firebase token not found")
        return repository.login(microsoftAccessToken, firebaseToken, deviceId)
    }
}