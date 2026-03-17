package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.data.remote.dto.semester.SemesterResponse

class SemesterApi(
    private val client: HttpClient
) {
    suspend fun getSemesters(): SemesterResponse {
        return client.get("/api/v1/semester/student").body()
    }
}