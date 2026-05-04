package org.example.project.data.remote.dto.week_schedule

import kotlinx.serialization.Serializable

@Serializable
data class CourseClass(
    val classCode: String,
    val dayOfWeek: Int,
    val subjectName: String,
    val subjectCode: String,
    val credits: Int?,
    val startPeriod: Int,
    val endPeriod: Int,
    val startTime: String,
    val endTime: String,
    val room: String,
    val lecturer: Lecturer
)