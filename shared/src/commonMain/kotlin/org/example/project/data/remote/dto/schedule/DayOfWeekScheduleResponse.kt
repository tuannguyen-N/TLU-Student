package org.example.project.data.remote.dto.schedule

import kotlinx.serialization.Serializable

@Serializable
data class DayOfWeekScheduleResponse(
    val code: Int,
    val message: String,
    val data: Data
)