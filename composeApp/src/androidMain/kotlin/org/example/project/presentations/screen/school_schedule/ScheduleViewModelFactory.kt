package org.example.project.presentations.screen.school_schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.usecase.ScheduleUseCase

class ScheduleViewModelFactory(
    private val scheduleUseCase: ScheduleUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            return ScheduleViewModel(scheduleUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}