package org.example.project.presentations.screen.timetable_offline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.domain.usecase.SemesterUseCase

class OfflineTimetableViewModelFactory(
    private val scheduleUseCase: ScheduleUseCase,
    private val semesterUseCase: SemesterUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OfflineTimetableViewModel::class.java)) {
            return OfflineTimetableViewModel(scheduleUseCase, semesterUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
