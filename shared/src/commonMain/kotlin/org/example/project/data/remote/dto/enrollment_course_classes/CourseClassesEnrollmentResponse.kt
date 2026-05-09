package org.example.project.data.remote.dto.enrollment_course_classes

import kotlinx.serialization.Serializable

@Serializable
data class CourseClassesEnrollmentResponse(
    val code: Int,
    val data: List<CourseClassEnrollmentData>?,
    val message: String
)