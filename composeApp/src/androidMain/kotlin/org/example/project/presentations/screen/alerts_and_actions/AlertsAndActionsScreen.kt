package org.example.project.presentations.screen.alerts_and_actions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.screen.alerts_and_actions.components.AlertsAndActionsContent
import org.example.project.presentations.utils.CollectWithLifecycle

@Composable
fun AlertsAndActionsScreen(
    viewModel: AlertsAndActionsViewModel,
    onBack: () -> Unit,
    onNavigateToExamSchedule: () -> Unit,
    onNavigateToTuition: (notificationId: Int) -> Unit
) {
    val alertList by viewModel.alertList.collectAsStateWithLifecycle()

    viewModel.uiEvent.CollectWithLifecycle { event ->
        when (event) {
            AlertsAndActionUiEvent.NavigateToExamScheduleScreen -> onNavigateToExamSchedule()
            is AlertsAndActionUiEvent.NavigateToTuitionScreen -> onNavigateToTuition(event.notificationId)
        }
    }

    StatusBarStyle(true)

    AlertsAndActionsContent(
        items = alertList,
        onBack = onBack,
        onAction = viewModel::onAction
    )
}