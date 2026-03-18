package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.data.cache.CacheManager
import org.example.project.data.remote.api.ExamScheduleApi
import org.example.project.data.remote.dto.exam_schedule.ExamSchedule
import org.example.project.data.remote.dto.exam_schedule.ExamScheduleData
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
    ): Result<ExamScheduleData> {
        return runCatching {
            val data = examScheduleCache.getOrFetch(
                key = semester,
                forceRefresh = forceReset
            ){
                examScheduleApi.getExamSchedules(semester).data!!
            }
            _examSchedules.update { data.examSchedules }
            data
        }
    }
}