package org.example.project.data.remote.dto.semester_schedule

import kotlinx.serialization.Serializable

@Serializable
data class SemesterScheduleResponse(
    val code: Int,
    val data: SemesterSchedule?,
    val message: String
)