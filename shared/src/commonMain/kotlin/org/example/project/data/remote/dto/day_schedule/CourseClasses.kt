package org.example.project.data.remote.dto.day_schedule

import kotlinx.serialization.Serializable

@Serializable
data class CourseClasses(
    val courseClasses: List<CourseClass>
)