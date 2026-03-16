package org.example.project.domain.usecase

import org.example.project.domain.repository.ScheduleRepository

class ScheduleUseCase(
    private val repository: ScheduleRepository
) {
    val daySchedule = repository.daySchedules
    val weekSchedule = repository.weekSchedules

    suspend fun getDaySchedule(dayOfWeek: Int): Result<Any>{
        if (dayOfWeek !in 1..8) {
            return Result.failure(IllegalArgumentException("Ngày không hợp lệ"))
        }
        return repository.getDaySchedule(dayOfWeek)
    }

    suspend fun getWeekSchedule(startDate: String, endDate: String): Result<Any>{
        return repository.getWeekSchedule(startDate, endDate)
    }
}