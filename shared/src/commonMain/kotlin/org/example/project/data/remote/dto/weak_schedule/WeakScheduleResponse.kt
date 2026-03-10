package org.example.project.data.remote.dto.weak_schedule

import kotlinx.serialization.Serializable

@Serializable
data class WeakScheduleResponse(
    val code: Int,
    val data: WeekSchedule?,
    val message: String
)