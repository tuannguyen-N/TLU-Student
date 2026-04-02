package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.data.cache.CacheManager
import org.example.project.data.local.dao.ScheduleDao
import org.example.project.data.mapper.toSubjects
import org.example.project.data.mapper.toWeeklyScheduleData
import org.example.project.data.mapper.toWeeklyScheduleEntity
import org.example.project.data.remote.api.ScheduleApi
import org.example.project.data.remote.dto.day_schedule.ScheduleData
import org.example.project.data.remote.dto.week_schedule.WeeklyScheduleData
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.SubjectItem
import kotlin.time.Duration.Companion.minutes

class ScheduleRepository(
    private val scheduleApi: ScheduleApi,
    private val scheduleDao: ScheduleDao
) {
    private val dayScheduleCache = CacheManager<Int, ScheduleData>(5.minutes)
    private val weekScheduleCache = CacheManager<String, WeeklyScheduleData>(10.minutes)

    private val _daySchedules = MutableStateFlow<Map<Int, ScheduleData>>(emptyMap())
    val daySchedules = _daySchedules.asStateFlow()

    private val _weekSchedules = MutableStateFlow<Map<String, WeeklyScheduleData>>(emptyMap())
    val weekSchedules = _weekSchedules.asStateFlow()

    suspend fun getDaySchedule(
        dayOfWeek: Int,
        forceRefresh: Boolean = false
    ): AppResult<ScheduleData> {
        return try {
            val data = dayScheduleCache.getOrFetch(
                key = dayOfWeek,
                forceRefresh = forceRefresh
            ) {
                scheduleApi.getDayOfWeekSchedule(dayOfWeek).data
                    ?: throw Exception("Không có dữ liệu lịch học")
            }
            _daySchedules.update { it + (dayOfWeek to data) }
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }

    suspend fun getWeekSchedule(
        startDate: String,
        endDate: String,
        forceRefresh: Boolean = false
    ): AppResult<WeeklyScheduleData> {
        val key = "$startDate - $endDate"
        return try {
            val data = weekScheduleCache.getOrFetch(
                key = key,
                forceRefresh = forceRefresh
            ) {
                scheduleApi.getWeakSchedule(startDate, endDate).data
                    ?: throw Exception("Không có dữ liệu lịch tuần")
            }
            scheduleDao.insertDaySchedule(data.toWeeklyScheduleEntity())
            _weekSchedules.update { it + (key to data) }
            AppResult.Success(data)

        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }

    suspend fun getSemesterSubjects(semester: String): AppResult<List<SubjectItem>> {
        return try {
            val data = scheduleApi.getSemesterSubjects(semester).data
                ?.toSubjects()
                ?: emptyList()
            AppResult.Success(data)

        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }

    suspend fun getWeekScheduleOffLine(
        startDate: String,
        endDate: String
    ): AppResult<WeeklyScheduleData?> {
        return try {
            val data = scheduleDao.getWeeklySchedule(startDate, endDate)?.toWeeklyScheduleData()
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }

    }

    fun clearCache() {
        dayScheduleCache.clear()
        weekScheduleCache.clear()
        _daySchedules.value = emptyMap()
        _weekSchedules.value = emptyMap()
    }
}