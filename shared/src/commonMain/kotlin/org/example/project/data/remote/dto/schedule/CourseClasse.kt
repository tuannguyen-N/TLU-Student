package org.example.project.data.remote.dto.schedule

import kotlinx.serialization.Serializable

@Serializable
data class CourseClass(
    val classCode: String,
    val dayOfWeek: Int,
    val endPeriod: Int,
    val endTime: String,
    val lecturerEmail: String,
    val lecturerName: String,
    val room: String,
    val startPeriod: Int,
    val startTime: String,
    val subjectCode: String,
    val subjectName: String
)