package org.example.project.data.remote.dto.enroll

import kotlinx.serialization.Serializable

@Serializable
data class CourseEnrollmentResponse(
    val code: Int,
    val data: CourseEnrollmentData?,
    val message: String
)