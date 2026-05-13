package org.example.project.presentations.screen.application_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.ApplicationRepository

class ApplicationDetailViewModelFactory(
    private val repository: ApplicationRepository,
    private val id: Int
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApplicationDetailViewModel::class.java)) {
            return ApplicationDetailViewModel(repository, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
