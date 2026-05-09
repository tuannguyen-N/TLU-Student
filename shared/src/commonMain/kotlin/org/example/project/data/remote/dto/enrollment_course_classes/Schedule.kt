package org.example.project.data.remote.dto.enrollment_course_classes

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val dayOfWeek: Int,
    val endPeriod: Int,
    val room: String,
    val startPeriod: Int
)