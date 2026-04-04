package org.example.project.presentations.screen.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.data.remote.interceptor.AuthPluginConfig
import org.example.project.domain.usecase.StudentUseCase

class EditProfileViewModelFactory(
    private val studentUseCase: StudentUseCase,
    private val authPluginConfig: AuthPluginConfig
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(studentUseCase, authPluginConfig) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}