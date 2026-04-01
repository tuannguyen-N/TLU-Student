package org.example.project.presentations.screen.exam_schedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.screen.exam_schedule.components.ExamScheduleContent

@Composable
fun ExamScheduleScreen(
    viewModel: ExamScheduleViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StatusBarStyle(darkIcons = true)

    ExamScheduleContent(
        uiState = uiState,
        onBack = onBack,
        onTabSelected = viewModel::onTabSelected,
        onToggleDropdown = viewModel::onToggleDropdown,
        onSemesterChanged = viewModel::onSemesterChanged,
        onChangeDate = viewModel::onChangeDate,
        onResetData = viewModel::onResetData
    )
}