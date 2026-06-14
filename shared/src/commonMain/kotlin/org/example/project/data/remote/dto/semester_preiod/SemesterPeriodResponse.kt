package org.example.project.data.remote.dto.semester_preiod

import kotlinx.serialization.Serializable

@Serializable
data class SemesterPeriodResponse(
    val code: Int,
    val data: SemesterPeriodData?,
    val message: String
)