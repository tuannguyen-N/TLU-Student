package org.example.project.domain.usecase

import org.example.project.domain.repository.ScheduleRepository

class ScheduleUseCase(
    private val repository: ScheduleRepository
) {
    val dayOfWeekSchedule = repository.dayOfWeekSchedule

    suspend fun getDayOfWeekSchedule(dayOfWeek: Int): Result<Any>{
        if (dayOfWeek !in 1..8) {
            return Result.failure(IllegalArgumentException("Ngày không hợp lệ"))
        }
        return repository.getDayOfWeekSchedule(dayOfWeek)
    }
}