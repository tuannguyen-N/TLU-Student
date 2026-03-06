package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.remote.api.ScheduleApi
import org.example.project.data.remote.dto.schedule.DayOfWeekScheduleResponse

class ScheduleRepository(
    private val scheduleApi: ScheduleApi
) {
    private val _dayOfWeekSchedule = MutableStateFlow<DayOfWeekScheduleResponse?>(null)
    val dayOfWeekSchedule = _dayOfWeekSchedule.asStateFlow()

    private val scheduleCache = mutableMapOf<Int, DayOfWeekScheduleResponse>()

    suspend fun getDayOfWeekSchedule(dayOfWeek: Int): Result<DayOfWeekScheduleResponse> {
        scheduleCache[dayOfWeek]?.let { cached ->
            _dayOfWeekSchedule.value = cached
            return Result.success(cached)
        }

        return runCatching { scheduleApi.getDayOfWeekSchedule(dayOfWeek) }.onSuccess {
            scheduleCache[dayOfWeek] = it
            _dayOfWeekSchedule.value = it
        }
    }
}