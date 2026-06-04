package org.example.project.data.remote.dto.student_search

import kotlinx.serialization.Serializable

@Serializable
data class StudentSummary(
    val studentCode: String,
    val fullName: String
)