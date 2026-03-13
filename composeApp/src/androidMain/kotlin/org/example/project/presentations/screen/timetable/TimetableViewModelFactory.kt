package org.example.project.presentations.screen.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.domain.usecase.SemesterUseCase

class TimetableViewModelFactory(
    private val scheduleUseCase: ScheduleUseCase,
    private val semesterUseCase: SemesterUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimetableViewModel::class.java)) {
            return TimetableViewModel(scheduleUseCase, semesterUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}