package org.example.project.presentations.screen.alerts_and_actions

sealed interface AlertsAndActionUiEvent {
    object NavigateToExamScheduleScreen: AlertsAndActionUiEvent
    class NavigateToTuitionScreen(val notificationId: Int): AlertsAndActionUiEvent
}