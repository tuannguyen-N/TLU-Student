package org.example.project.di

import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.api.AuthApi
import org.example.project.data.remote.api.StudentApi
import org.example.project.data.remote.createHttpClient
import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.repository.StudentRepository
import org.example.project.domain.usecase.LoginUseCase
import org.example.project.domain.usecase.StudentUseCase

class AppContainer(
    tokenStorage: TokenStorage
) {
    private val httpClient = createHttpClient(tokenStorage)

    // for auth
    private val authApi = AuthApi(httpClient)
    private val authRepository = AuthRepository(
        authApi = authApi,
        tokenStorage = tokenStorage
    )
    val loginUseCase = LoginUseCase(authRepository)

    //for student
    private val studentApi = StudentApi(httpClient)
    private val studentRepository = StudentRepository(studentApi)
    val studentUseCase = StudentUseCase(studentRepository)
}