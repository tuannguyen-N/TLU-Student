package org.example.project.presentations.screen.alerts_and_actions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.NotificationRepository
import org.example.project.domain.usecase.StudentUseCase

class AlertsAndActionsViewModelFactory(
    private val notificationRepository: NotificationRepository,
    private val studentUseCase: StudentUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(AlertsAndActionsViewModel::class.java)) {
            return AlertsAndActionsViewModel(notificationRepository, studentUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}