package org.example.project.presentations.screen.class_signed_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.EnrollmentRepository

class SignedUpClassViewModelFactory(
    private val enrollmentRepository: EnrollmentRepository,
    private val semesterId: Int
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignedUpClassesViewModel::class.java)) {
            return SignedUpClassesViewModel(enrollmentRepository, semesterId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}