package org.example.project.domain.model

import org.example.project.data.remote.dto.weak_schedule.WeekSchedule

data class TimetableUiState(
    val weekSchedule: WeekSchedule? = null,
    val isShowDetailSubjectDialog: Boolean = false,
    val isLoading: Boolean = false
)