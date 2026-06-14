package org.example.project.presentations.screen.temp_timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.EnrollmentRepository

class TempTimetableViewModelFactory(
    private val enrollmentRepository: EnrollmentRepository,
    private val semesterId: Int
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TempTimetableViewModel::class.java)) {
            return TempTimetableViewModel(enrollmentRepository, semesterId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
