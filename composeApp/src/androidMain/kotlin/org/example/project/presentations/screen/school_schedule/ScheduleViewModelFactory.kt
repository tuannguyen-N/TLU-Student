package org.example.project.presentations.screen.school_schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.NotificationRepository
import org.example.project.domain.usecase.ScheduleUseCase

class ScheduleViewModelFactory(
    private val scheduleUseCase: ScheduleUseCase,
    private val notificationRepository: NotificationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            return ScheduleViewModel(scheduleUseCase, notificationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}