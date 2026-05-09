package org.example.project.data.remote.dto.enrollment_course_classes

import kotlinx.serialization.Serializable

@Serializable
data class CourseClassEnrollmentData(
    val capacity: Int,
    val classCode: String,
    val className: String,
    val enrolledCount: Int,
    val id: Int,
    val lecturerCode: String,
    val lecturerName: String,
    val schedules: List<Schedule>
)