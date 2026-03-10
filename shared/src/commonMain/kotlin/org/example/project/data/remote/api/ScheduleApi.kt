package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.example.project.data.remote.dto.day_schedule.DayOfWeekScheduleResponse
import org.example.project.data.remote.dto.weak_schedule.WeakScheduleResponse

class ScheduleApi(
    private val client: HttpClient
) {
    suspend fun getDayOfWeekSchedule(dayOfWeek: Int): DayOfWeekScheduleResponse {
        return client.get("/api/v1/student/schedules/") {
            parameter("day-of-week", dayOfWeek)
        }.body()
    }

    suspend fun getWeakSchedule(startDate: String, endDate: String): WeakScheduleResponse {
        return client.get("api/v1/student/schedules/weekly") {
            parameter("start_date", startDate)
            parameter("end_date", endDate)
        }.body()
    }
}