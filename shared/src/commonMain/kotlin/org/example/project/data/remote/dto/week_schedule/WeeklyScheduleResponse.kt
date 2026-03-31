package org.example.project.data.remote.dto.week_schedule

import kotlinx.serialization.Serializable

@Serializable
data class WeeklyScheduleResponse(
    val code: Int,
    val data: WeeklyScheduleData?,
    val message: String
)