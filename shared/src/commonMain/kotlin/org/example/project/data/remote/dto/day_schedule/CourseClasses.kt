package org.example.project.data.remote.dto.day_schedule

import kotlinx.serialization.Serializable
import org.example.project.data.remote.dto.week_schedule.CourseClass

@Serializable
data class CourseClasses(
    val courseClasses: List<CourseClass>
)