package org.example.project.presentations.screen.alerts_and_actions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import org.example.project.domain.model.AlertActionItem
import org.example.project.domain.model.NotificationReferenceType
import org.example.project.domain.repository.NotificationRepository
import org.example.project.domain.usecase.StudentUseCase

class AlertsAndActionsViewModel(
    val notificationRepository: NotificationRepository,
    val studentUseCase: StudentUseCase
) : ViewModel() {

    val alertList =
        notificationRepository.getAlertList(studentUseCase.studentInfo.value?.studentCode!!)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    private val _uiEvent = Channel<AlertsAndActionUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(alertActionItem: AlertActionItem) {
        when (alertActionItem.referenceType) {
            NotificationReferenceType.EXAM_SCHEDULE -> sendUiEvent(AlertsAndActionUiEvent.NavigateToExamScheduleScreen)
            NotificationReferenceType.TUITION -> sendUiEvent(
                AlertsAndActionUiEvent.NavigateToTuitionScreen(
                    alertActionItem.id
                )
            )

            else -> Unit
        }
    }

    private fun sendUiEvent(event: AlertsAndActionUiEvent) {
        _uiEvent.trySend(event)
    }
}