package org.example.project.domain.usecase

import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.model.ApiResult

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(microsoftAccessToken: String): ApiResult<Unit> {
        return repository.login(microsoftAccessToken)
    }
}