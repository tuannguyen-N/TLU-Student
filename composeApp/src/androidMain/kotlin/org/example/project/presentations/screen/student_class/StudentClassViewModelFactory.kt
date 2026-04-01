package org.example.project.presentations.screen.student_class

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.StudentClassRepository

class StudentClassViewModelFactory(
    private val studentClassRepository: StudentClassRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(StudentClassViewModel::class.java)) {
            return StudentClassViewModel(studentClassRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}