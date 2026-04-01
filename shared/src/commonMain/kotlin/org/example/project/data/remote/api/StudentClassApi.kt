package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.data.remote.dto.student_class.StudentClassInfoResponse

class StudentClassApi(
    private val client: HttpClient
) {
    suspend fun getStudentClassInfo(): StudentClassInfoResponse{
        return client.get("/api/v1/students/me/class").body()
    }
}