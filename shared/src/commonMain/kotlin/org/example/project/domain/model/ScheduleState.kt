package org.example.project.domain.model

import org.example.project.data.remote.dto.schedule.CourseClass

data class ScheduleState(
    val courseClasses: List<CourseClass>? = emptyList(),
    val selectedCourseClass: CourseClass? = null,
    val selectedDayOfWeek: Int,
    val showDetailCourseClass: Boolean = false,
    val isLoading: Boolean = false
)