package org.example.project.presentations.screen.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.ApplicationRepository

class ApplicationViewModelFactory(
    private val applicationRepository: ApplicationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ApplicationViewModel::class.java)) {
            return ApplicationViewModel(applicationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}