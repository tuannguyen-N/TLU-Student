package org.example.project.domain.model

import org.example.project.data.remote.dto.week_schedule.CourseClass

data class ScheduleState(
    val courseClasses: List<CourseClass>? = emptyList(),
    val selectedCourseClass: CourseClass? = null,
    val showDetailCourseClass: Boolean = false,
    val showDetailLecturerInfo: Boolean = false,
    val currentDay: Int,
    val selectedDayOfWeek: Int,

    val isLoading: Boolean = false
)