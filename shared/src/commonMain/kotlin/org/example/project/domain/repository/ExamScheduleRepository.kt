package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.data.cache.CacheManager
import org.example.project.data.mapper.toDaySchedule
import org.example.project.data.remote.api.ExamScheduleApi
import org.example.project.data.remote.dto.exam_schedule.ExamSchedule
import org.example.project.data.remote.dto.exam_schedule.ExamScheduleData
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.DaySchedule
import kotlin.time.Duration.Companion.minutes

class ExamScheduleRepository(
    private val examScheduleApi: ExamScheduleApi
) {
    private val examScheduleCache = CacheManager<String, ExamScheduleData>(5.minutes)

    private val _examSchedules = MutableStateFlow<List<ExamSchedule>>(emptyList())
    val examSchedules = _examSchedules.asStateFlow()

    suspend fun getExamSchedules(
        semester: String,
        forceReset: Boolean = false
    ): AppResult<ExamScheduleData> {
        return try {
            val data = examScheduleCache.getOrFetch(
                key = semester,
                forceRefresh = forceReset
            ) {
                examScheduleApi.getExamSchedules(semester).data
                    ?: throw Exception("Không có dữ liệu lịch thi")
            }
            _examSchedules.update { data.examSchedules }
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }

    suspend fun getExamDaySchedule(
        semester: String
    ): AppResult<List<DaySchedule>> {
        return try {
            val data = examScheduleApi.getExamSchedules(semester).data
                ?: throw Exception("Không có dữ liệu lịch thi")
            AppResult.Success(data.toDaySchedule())
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }
}