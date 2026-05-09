package org.example.project.presentations.screen.class_sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.EnrollmentRepository
import org.example.project.domain.repository.SemesterRepository
import org.example.project.domain.usecase.SemesterUseCase

class ClassSignUpViewModelFactory(
    private val enrollmentRepository: EnrollmentRepository,
    private val semesterUseCase: SemesterUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClassSignUpViewModel::class.java)) {
            return ClassSignUpViewModel(enrollmentRepository,semesterUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}