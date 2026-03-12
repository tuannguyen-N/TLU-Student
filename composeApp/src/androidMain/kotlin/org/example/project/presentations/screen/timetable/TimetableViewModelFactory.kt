package org.example.project.presentations.screen.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.usecase.ScheduleUseCase

class TimetableViewModelFactory(
    private val scheduleUseCase: ScheduleUseCase
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimetableViewModel::class.java)) {
            return TimetableViewModel(scheduleUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}