package org.example.project.presentations.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.DeviceProvider
import org.example.project.domain.usecase.LoginUseCase

class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val deviceProvider: DeviceProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginUseCase = loginUseCase,
                deviceProvider = deviceProvider
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}