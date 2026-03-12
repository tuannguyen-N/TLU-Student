package org.example.project.data.remote.dto.week_schedule

import kotlinx.serialization.Serializable

@Serializable
data class WeekScheduleResponse(
    val code: Int,
    val data: WeekSchedule?,
    val message: String
)