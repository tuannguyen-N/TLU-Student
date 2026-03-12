package org.example.project.data.remote.dto.week_schedule

import kotlinx.serialization.Serializable

@Serializable
data class Lecturer(
    val lecturerCode: String?,
    val fullName: String,
    val phoneNumber: String?,
    val email: String
)