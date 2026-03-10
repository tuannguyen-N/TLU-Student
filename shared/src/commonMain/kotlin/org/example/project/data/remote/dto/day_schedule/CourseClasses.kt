package org.example.project.data.remote.dto.schedule

import kotlinx.serialization.Serializable

@Serializable
data class CourseClasses(
    val courseClasses: List<CourseClass>
)