package org.example.project.presentations.screen.class_signed_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.EnrollmentRepository
import org.example.project.domain.usecase.SemesterUseCase

class SignedUpClassViewModelFactory(
    private val enrollmentRepository: EnrollmentRepository,
    private val semesterUseCase: SemesterUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignedUpClassesViewModel::class.java)) {
            return SignedUpClassesViewModel(enrollmentRepository, semesterUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}