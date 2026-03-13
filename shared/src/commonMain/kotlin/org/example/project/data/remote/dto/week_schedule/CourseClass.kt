package org.example.project.data.remote.dto.week_schedule

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Clock

@Serializable
data class CourseClass(
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