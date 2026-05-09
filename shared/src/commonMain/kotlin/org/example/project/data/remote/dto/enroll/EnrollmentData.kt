package org.example.project.data.remote.dto.enroll

import kotlinx.serialization.Serializable

@Serializable
data class EnrollmentData(
    val groupId: Int,
    val missingSubjectCodes: List<String>,
    val needMore: Int
)