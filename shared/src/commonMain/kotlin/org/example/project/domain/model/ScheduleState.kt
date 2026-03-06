package org.example.project.domain.model

import org.example.project.data.remote.dto.schedule.CourseClass

data class ScheduleState(
    val courseClasses: List<CourseClass>? = emptyList(),
    val selectedDayOfWeek: Int,
    val isLoading: Boolean = false
)