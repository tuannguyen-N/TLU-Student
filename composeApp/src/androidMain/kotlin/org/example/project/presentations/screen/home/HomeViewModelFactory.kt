package org.example.project.presentations.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.usecase.StudentUseCase

class HomeViewModelFactory(
    private val studentUseCase: StudentUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                studentUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}