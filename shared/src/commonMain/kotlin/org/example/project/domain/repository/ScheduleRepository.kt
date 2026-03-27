package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.data.cache.CacheManager
import org.example.project.data.mapper.toSubjects
import org.example.project.data.remote.api.ScheduleApi
import org.example.project.data.remote.dto.day_schedule.CourseClasses
import org.example.project.data.remote.dto.week_schedule.WeekSchedule
import org.example.project.domain.model.ApiResult
import org.example.project.domain.model.SubjectItem
import kotlin.time.Duration.Companion.minutes

class ScheduleRepository(
    private val scheduleApi: ScheduleApi
) {
    private val dayScheduleCache = CacheManager<Int, CourseClasses>(5.minutes)
    private val weekScheduleCache = CacheManager<String, WeekSchedule>(10.minutes)

    private val _daySchedules = MutableStateFlow<Map<Int, CourseClasses>>(emptyMap())
    val daySchedules = _daySchedules.asStateFlow()

    private val _weekSchedules = MutableStateFlow<Map<String, WeekSchedule>>(emptyMap())
    val weekSchedules = _weekSchedules.asStateFlow()

    suspend fun getDaySchedule(
        dayOfWeek: Int,
        forceRefresh: Boolean = false
    ): ApiResult<CourseClasses> {
        return try {
            val data = dayScheduleCache.getOrFetch(
                key = dayOfWeek,
                forceRefresh = forceRefresh
            ) {
                scheduleApi.getDayOfWeekSchedule(dayOfWeek).data
                    ?: throw Exception("Không có dữ liệu lịch học")
            }
            _daySchedules.update { it + (dayOfWeek to data) }
            ApiResult.Success(data)

        } catch (e: Exception) {
            ApiResult.Failure(message = e.message, cause = e)
        }
    }

    suspend fun getWeekSchedule(
        startDate: String,
        endDate: String,
        forceRefresh: Boolean = false
    ): ApiResult<WeekSchedule> {
        val key = "$startDate - $endDate"
        return try {
            val data = weekScheduleCache.getOrFetch(
                key = key,
                forceRefresh = forceRefresh
            ) {
                scheduleApi.getWeakSchedule(startDate, endDate).data
                    ?: throw Exception("Không có dữ liệu lịch tuần")
            }
            _weekSchedules.update { it + (key to data) }
            ApiResult.Success(data)

        } catch (e: Exception) {
            ApiResult.Failure(message = e.message, cause = e)
        }
    }

    suspend fun getSemesterSubjects(semester: String): ApiResult<List<SubjectItem>> {
        return try {
            val data = scheduleApi.getSemesterSubjects(semester).data
                ?.toSubjects()
                ?: emptyList()
            ApiResult.Success(data)

        } catch (e: Exception) {
            ApiResult.Failure(message = e.message, cause = e)
        }
    }

    fun clearCache() {
        dayScheduleCache.clear()
        weekScheduleCache.clear()
        _daySchedules.value = emptyMap()
        _weekSchedules.value = emptyMap()
    }
}