package org.example.project.data.remote.dto.schedule

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val courseClasses: List<CourseClasses>
)