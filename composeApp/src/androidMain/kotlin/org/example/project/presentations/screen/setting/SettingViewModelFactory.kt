package org.example.project.presentations.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.data.local.AppPreferences
import org.example.project.domain.usecase.LogoutUseCase
import org.example.project.presentations.utils.NotificationPermissionManager

class SettingViewModelFactory(
    private val logoutUseCase: LogoutUseCase,
    private val permissionManager: NotificationPermissionManager,
    private val prefs: AppPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(
                logoutUseCase = logoutUseCase,
                permissionManager = permissionManager,
                prefs = prefs
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}