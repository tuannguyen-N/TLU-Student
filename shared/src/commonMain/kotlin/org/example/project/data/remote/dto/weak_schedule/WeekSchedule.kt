package org.example.project.data.remote.dto.weak_schedule

import kotlinx.serialization.Serializable

@Serializable
data class WeakSchedule(
    val dailySchedules: List<DailySchedule>,
    val endDate: String,
    val semester: String,
    val startDate: String,
    val week: Int
)