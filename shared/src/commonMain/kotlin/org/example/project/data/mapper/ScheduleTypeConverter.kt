package org.example.project.data.mapper

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import org.example.project.data.remote.dto.week_schedule.DailySchedule

class ScheduleTypeConverter {
    @TypeConverter
    fun fromDailySchedules(value: List<DailySchedule>): String{
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toDailySchedules(value: String): List<DailySchedule>{
        return Json.decodeFromString(value)
    }
}