package org.example.project.data.remote.dto.enroll

import kotlinx.serialization.Serializable
import org.example.project.data.remote.dto.week_schedule.Lecturer

@Serializable
data class EnrollmentScheduleData(
    val classCode: String,
    val credits: Int,
    val dayOfWeek: Int,
    val endPeriod: Int,
    val endTime: String,
    val lecturer: Lecturer,
    val room: String,
    val startPeriod: Int,
    val startTime: String,
    val subjectCode: String,
    val subjectName: String
)