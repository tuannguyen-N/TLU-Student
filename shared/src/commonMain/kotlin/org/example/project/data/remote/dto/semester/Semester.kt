package org.example.project.data.remote.dto.semester

import kotlinx.serialization.Serializable

@Serializable
data class Semester(
    val endDate: String,
    val semesterName: String,
    val startDate: String
)