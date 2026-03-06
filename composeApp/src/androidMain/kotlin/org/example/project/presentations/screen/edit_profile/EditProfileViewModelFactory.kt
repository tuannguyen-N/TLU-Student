package org.example.project.presentations.screen.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.usecase.StudentUseCase

class EditProfileViewModelFactory(
    private val studentUseCase: StudentUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(studentUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}