package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.example.project.data.remote.dto.exam_schedule.ExampleScheduleResponse

class ExamScheduleApi(
    private val client: HttpClient
) {
    suspend fun getExamSchedules(semester: String): ExampleScheduleResponse{
        return client.get("/api/v1/student/exams"){
            parameter("HocKy", semester)
        }.body()
    }
}