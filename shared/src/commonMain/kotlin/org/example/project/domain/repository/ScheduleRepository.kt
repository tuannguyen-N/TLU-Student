package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.remote.api.ScheduleApi
import org.example.project.data.remote.dto.day_schedule.CourseClasses
import org.example.project.data.remote.dto.day_schedule.DayOfWeekScheduleResponse
import org.example.project.data.remote.dto.week_schedule.WeekSchedule
import org.example.project.domain.model.CacheEntry
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

class ScheduleRepository(
    private val scheduleApi: ScheduleApi
) {
    companion object {
        private val DAY_SCHEDULE_TTL = 5.minutes
        private val WEEK_SCHEDULE_TTL = 10.minutes
    }

    private val _dayOfWeekSchedule = MutableStateFlow<CourseClasses?>(null)
    val dayOfWeekSchedule = _dayOfWeekSchedule.asStateFlow()

    private val _weekSchedule = MutableStateFlow<WeekSchedule?>(null)
    val weekSchedule = _weekSchedule.asStateFlow()

    private val dayScheduleCache = mutableMapOf<Int, CacheEntry<DayOfWeekScheduleResponse>>()
    private val weekScheduleCache = mutableMapOf<String, CacheEntry<WeekSchedule>>()

    suspend fun getDayOfWeekSchedule(dayOfWeek: Int): Result<Any> {
        dayScheduleCache[dayOfWeek]
            ?.takeIf { !it.isExpired(DAY_SCHEDULE_TTL) }
            ?.let {
                _dayOfWeekSchedule.value = it.data.data
                return Result.success(Unit)
            }

        return runCatching { scheduleApi.getDayOfWeekSchedule(dayOfWeek) }.onSuccess {
            val data = it.data ?: return@onSuccess
            dayScheduleCache[dayOfWeek] = CacheEntry(it)
            _dayOfWeekSchedule.value = data
        }
    }

    suspend fun getWeekSchedule(startDate: String, endDate: String): Result<Any> {
        val cacheKey = "$startDate-$endDate"

        weekScheduleCache[cacheKey]
            ?.takeIf { !it.isExpired(WEEK_SCHEDULE_TTL) }
            ?.let {
                _weekSchedule.value = it.data
                return Result.success(Unit)
            }

        return runCatching { scheduleApi.getWeakSchedule(startDate, endDate) }.onSuccess {
            val data = it.data ?: return@onSuccess
            weekScheduleCache[cacheKey] = CacheEntry(data)
            _weekSchedule.value = data
        }
    }

    fun clearCache() {
        dayScheduleCache.clear()
        weekScheduleCache.clear()
    }
}