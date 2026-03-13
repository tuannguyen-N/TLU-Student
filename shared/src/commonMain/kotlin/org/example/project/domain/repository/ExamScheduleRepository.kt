package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.remote.api.ExamScheduleApi
import org.example.project.data.remote.dto.exam_schedule.ExamSchedule
import org.example.project.data.remote.dto.exam_schedule.ExampleScheduleResponse

class ExamScheduleRepository(
    private val examScheduleApi: ExamScheduleApi
) {
    private val _examSchedules = MutableStateFlow<List<ExamSchedule>>(emptyList())
    val examSchedules = _examSchedules.asStateFlow()

    suspend fun getExamSchedules(semester: String): Result<ExampleScheduleResponse> {
        return runCatching { examScheduleApi.getExamSchedules(semester) }
            .onSuccess { _examSchedules.value = it.examScheduleData?.examSchedules?: emptyList() }
    }
}