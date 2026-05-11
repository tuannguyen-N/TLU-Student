package org.example.project.presentations.screen.alerts_and_actions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import org.example.project.domain.model.NotificationReferenceType
import org.example.project.domain.repository.NotificationRepository

class AlertsAndActionsViewModel(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    val alertList = notificationRepository.getFullAlertList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _uiEvent = Channel<AlertsAndActionUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(referenceType: NotificationReferenceType) {
        when(referenceType){
            NotificationReferenceType.EXAM_SCHEDULE -> sendUiEvent(AlertsAndActionUiEvent.NavigateToExamScheduleScreen)
            NotificationReferenceType.TUITION -> sendUiEvent(AlertsAndActionUiEvent.NavigateToTuitionScreen)
        }
    }

    private fun sendUiEvent(event: AlertsAndActionUiEvent) {
        _uiEvent.trySend(event)
    }
}