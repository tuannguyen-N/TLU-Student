package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.data.remote.dto.schedule.DayOfWeekScheduleResponse

class ScheduleApi(
    private val client: HttpClient
) {
    suspend fun getDayOfWeekSchedule(dayOfWeek: Int): DayOfWeekScheduleResponse {
        return client.get("/api/v1/student/schedules/day-of-week=$dayOfWeek").body()
    }
}