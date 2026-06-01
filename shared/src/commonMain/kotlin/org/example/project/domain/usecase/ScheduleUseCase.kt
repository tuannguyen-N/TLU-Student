package org.example.project.domain.usecase

import org.example.project.domain.model.AppResult
import org.example.project.domain.model.DaySchedule
import org.example.project.domain.model.SubjectItem
import org.example.project.domain.repository.ScheduleRepository

class ScheduleUseCase(
    private val repository: ScheduleRepository
) {
    val dayStudySchedule = repository.daySchedules
    val weekSchedule = repository.weekSchedules

    suspend fun getDayStudySchedule(dayOfWeek: Int, isRefresh: Boolean = false): AppResult<Any> {
        if (dayOfWeek !in 1..8) {
            return AppResult.Failure("Ngày không hợp lệ")
        }
        return repository.getDayStudySchedule(dayOfWeek, isRefresh)
    }

    suspend fun getSemesterSubjects(semester: String): AppResult<List<SubjectItem>> {
        return repository.getSemesterSubjects(semester)
    }

    suspend fun getDaySchedule(dayOfWeek: Int): AppResult<List<DaySchedule>> {
        if (dayOfWeek !in 1..8) {
            return AppResult.Failure("Ngày không hợp lệ")
        }
        return repository.getDaySchedule(dayOfWeek)
    }

    suspend fun getWeekSchedule(
        startDate: String,
        endDate: String,
        isOffline: Boolean = false
    ): AppResult<Any?> {
        return if (isOffline)
            repository.getWeekScheduleOffLine(startDate, endDate)
        else
            repository.getWeekSchedule(startDate, endDate)
    }
}