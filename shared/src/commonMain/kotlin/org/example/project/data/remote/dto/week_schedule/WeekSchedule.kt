package org.example.project.data.remote.dto.weak_schedule

import kotlinx.serialization.Serializable

@Serializable
data class WeekSchedule(
    val dailySchedules: List<DailySchedule>,
    val endDate: String,
    val semester: String,
    val startDate: String,
    val week: Int
)