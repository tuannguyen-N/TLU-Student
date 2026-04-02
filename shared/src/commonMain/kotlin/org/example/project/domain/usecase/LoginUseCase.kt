package org.example.project.domain.usecase

import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.model.AppResult

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(microsoftAccessToken: String): AppResult<Unit> {
        return repository.login(microsoftAccessToken)
    }
}