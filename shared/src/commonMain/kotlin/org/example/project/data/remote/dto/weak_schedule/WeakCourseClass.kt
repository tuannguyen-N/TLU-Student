package org.example.project.data.remote.dto.weak_schedule

import kotlinx.serialization.Serializable

@Serializable
data class WeakCourseClass(
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