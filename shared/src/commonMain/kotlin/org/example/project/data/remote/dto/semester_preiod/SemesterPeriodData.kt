package org.example.project.data.remote.dto.semester_preiod

import kotlinx.serialization.Serializable

@Serializable
data class SemesterPeriodData(
    val endTime: String,
    val semesterCode: String,
    val semesterId: Int,
    val semesterName: String,
    val startTime: String
)