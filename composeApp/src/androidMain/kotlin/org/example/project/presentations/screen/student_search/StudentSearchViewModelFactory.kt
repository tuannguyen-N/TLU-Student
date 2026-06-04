package org.example.project.presentations.screen.student_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.SearchHistoryRepository
import org.example.project.domain.usecase.StudentUseCase

class StudentSearchViewModelFactory(
    private val studentUseCase: StudentUseCase,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(StudentSearchViewModel::class.java)) {
            return StudentSearchViewModel(studentUseCase, searchHistoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}