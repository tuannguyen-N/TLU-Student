package org.example.project.domain.model

import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.WeekSchedule

data class TimetableUiState(
    val weekSchedule: WeekSchedule? = null,
    val schoolYears: List<String> = emptyList(),
    val currentSelectedYear: String = "",
    val selectedCourseClass: CourseClass? = null,

    val showDetailCourseClass: Boolean = false,
    val showDetailLecturerInfo: Boolean = false,
    val isLoading: Boolean = false
)