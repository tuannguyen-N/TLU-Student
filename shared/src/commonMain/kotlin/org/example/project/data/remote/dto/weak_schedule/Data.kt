package org.example.project.data.remote.dto.weak_schedule

data class Data(
    val dailySchedules: List<DailySchedule>,
    val endDate: String,
    val semester: String,
    val startDate: String,
    val week: Int
)