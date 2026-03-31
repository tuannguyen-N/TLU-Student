package org.example.project.data.remote.dto.semester

import kotlinx.serialization.Serializable

@Serializable
data class Semester(
    val id: Int,
    val semesterName: String,
    val semesterCode: String,
    val academicYears: String,
    val semesterNumber: Int,
    val startDate: String,
    val endDate: String,
    val isActive: Boolean
)