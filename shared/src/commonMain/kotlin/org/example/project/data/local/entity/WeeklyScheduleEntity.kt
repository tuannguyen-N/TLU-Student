package org.example.project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.project.data.remote.dto.week_schedule.DailySchedule

@Entity(tableName = "schedule")
data class WeeklyScheduleEntity(
    @PrimaryKey val id: String,
    val semester: String,
    val week: Int,
    val startDate: String,
    val endDate: String,
    val dailySchedules: List<DailySchedule>
)