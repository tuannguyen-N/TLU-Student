package org.example.project.data.remote.dto.week_schedule

import kotlinx.serialization.Serializable

@Serializable
data class WeeklyScheduleData(
    val semester: String,
    val week: Int,
    val startDate: String,
    val endDate: String,
    val dailySchedules: List<DailySchedule>
)