package org.example.project.domain.usecase

import org.example.project.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(microsoftAccessToken: String): Result<Unit> {
        return repository.login(microsoftAccessToken)
    }

    suspend fun logout(){
        repository.signOut()
    }
}