package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.remote.api.ScheduleApi
import org.example.project.data.remote.dto.day_schedule.CourseClasses
import org.example.project.data.remote.dto.day_schedule.DayOfWeekScheduleResponse
import org.example.project.data.remote.dto.weak_schedule.WeekSchedule

class ScheduleRepository(
    private val scheduleApi: ScheduleApi
) {
    private val _dayOfWeekSchedule = MutableStateFlow<CourseClasses?>(null)
    val dayOfWeekSchedule = _dayOfWeekSchedule.asStateFlow()

    private val _weakSchedule = MutableStateFlow<WeekSchedule?>(null)
    val weakSchedule = _weakSchedule.asStateFlow()

    private val dayScheduleCache = mutableMapOf<Int, DayOfWeekScheduleResponse>()

    suspend fun getDayOfWeekSchedule(dayOfWeek: Int): Result<Any> {
        dayScheduleCache[dayOfWeek]?.let {
            _dayOfWeekSchedule.value = it.data
            return Result.success(Unit)
        }

        return runCatching { scheduleApi.getDayOfWeekSchedule(dayOfWeek) }.onSuccess {
            dayScheduleCache[dayOfWeek] = it
            _dayOfWeekSchedule.value = it.data
        }
    }

    suspend fun getWeakSchedule(startDate: String, endDate: String): Result<Any>{
        return runCatching { scheduleApi.getWeakSchedule(startDate, endDate) }.onSuccess {
            _weakSchedule.value = it.data
        }
    }
}