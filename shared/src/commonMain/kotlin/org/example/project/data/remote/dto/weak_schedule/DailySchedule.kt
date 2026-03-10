package org.example.project.data.remote.dto.weak_schedule

import kotlinx.serialization.Serializable

@Serializable
data class DailySchedule(
    val courseClasses: List<WeakCourseClass>
)