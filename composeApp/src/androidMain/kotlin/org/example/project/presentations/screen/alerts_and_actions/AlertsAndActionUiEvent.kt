package org.example.project.presentations.screen.alerts_and_actions

sealed interface AlertsAndActionUiEvent {
    object NavigateToExamScheduleScreen: AlertsAndActionUiEvent
    object NavigateToTuitionScreen: AlertsAndActionUiEvent
}