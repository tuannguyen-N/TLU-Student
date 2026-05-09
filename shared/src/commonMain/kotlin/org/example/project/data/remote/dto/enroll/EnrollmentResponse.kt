package org.example.project.data.remote.dto.enroll

data class EnrollmentResponse(
    val code: Int,
    val data: List<EnrollmentData>,
    val message: String
)