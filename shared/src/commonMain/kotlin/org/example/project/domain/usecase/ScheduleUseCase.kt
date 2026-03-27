package org.example.project.domain.usecase

import org.example.project.domain.model.ApiResult
import org.example.project.domain.model.SubjectItem
import org.example.project.domain.repository.ScheduleRepository

class ScheduleUseCase(
    private val repository: ScheduleRepository
) {
    val daySchedule = repository.daySchedules
    val weekSchedule = repository.weekSchedules

    suspend fun getDaySchedule(dayOfWeek: Int): ApiResult<Any>{
        if (dayOfWeek !in 1..8) {
            return ApiResult.Failure("Ngày không hợp lệ")
        }
        return repository.getDaySchedule(dayOfWeek)
    }

    suspend fun getSemesterSubjects(semester: String): ApiResult<List<SubjectItem>>{
        return repository.getSemesterSubjects(semester)
    }

    suspend fun getWeekSchedule(startDate: String, endDate: String): ApiResult<Any>{
        return repository.getWeekSchedule(startDate, endDate)
    }
}