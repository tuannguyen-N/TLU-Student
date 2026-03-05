package org.example.project.di

import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.api.AuthApi
import org.example.project.data.remote.createHttpClient
import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.usecase.LoginUseCase

class AppContainer(
    tokenStorage: TokenStorage
) {
    private val httpClient = createHttpClient(tokenStorage)

    private val authApi = AuthApi(httpClient)

    private val authRepository = AuthRepository(
        authApi = authApi,
        tokenStorage = tokenStorage
    )

    val loginUseCase = LoginUseCase(authRepository)
}