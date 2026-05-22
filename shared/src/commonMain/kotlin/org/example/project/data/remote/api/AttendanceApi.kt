package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.remote.dto.Response
import org.example.project.data.remote.dto.attendance.AttendanceRequest

class AttendanceApi(
    private val client: HttpClient
) {
    suspend fun checkIn(request: AttendanceRequest): Response {
        return client.post("/api/v1/attendance/checkin") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
