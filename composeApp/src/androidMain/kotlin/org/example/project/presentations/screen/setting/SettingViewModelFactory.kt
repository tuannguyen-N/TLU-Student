package org.example.project.presentations.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.usecase.LogoutUseCase

class SettingViewModelFactory(
    private val logoutUseCase: LogoutUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(
                logoutUseCase = logoutUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}