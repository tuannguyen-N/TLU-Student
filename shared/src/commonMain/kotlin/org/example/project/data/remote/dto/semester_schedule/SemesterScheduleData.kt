package org.example.project.data.remote.dto.semester_schedule

import kotlinx.serialization.Serializable
import org.example.project.data.remote.dto.week_schedule.CourseClass

@Serializable
data class SemesterScheduleData(
    val courseClasses: List<CourseClass>,
    val semester: String
)