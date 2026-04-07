package org.example.project.presentations.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.DeviceProvider
import org.example.project.domain.usecase.HandleLoginSuccessUseCase

class LoginViewModelFactory(
    private val deviceProvider: DeviceProvider,
    private val handleLoginSuccessUseCase: HandleLoginSuccessUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                deviceProvider = deviceProvider,
                handleLoginSuccessUseCase = handleLoginSuccessUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}