package org.example.project.data.remote.dto.enroll

import kotlinx.serialization.Serializable

@Serializable
data class EnrollmentScheduleResponse(
    val code: Int,
    val data: List<EnrollmentScheduleData>?,
    val message: String
)