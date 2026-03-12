package org.example.project.data.remote.dto.week_schedule

import kotlinx.serialization.Serializable

@Serializable
data class WeekCourseClass(
    val classCode: String,
    val dayOfWeek: Int,
    val endPeriod: Int,
    val endTime: String,
    val room: String,
    val startPeriod: Int,
    val startTime: String,
    val subjectCode: String,
    val subjectName: String,
    val lecturer: Lecturer
)