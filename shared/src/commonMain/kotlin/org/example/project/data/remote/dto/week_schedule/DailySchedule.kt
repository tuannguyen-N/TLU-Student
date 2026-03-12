package org.example.project.data.remote.dto.week_schedule

import kotlinx.serialization.Serializable

@Serializable
data class DailySchedule(
    val courseClasses: List<CourseClass>
)