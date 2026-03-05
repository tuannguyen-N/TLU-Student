package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.data.remote.dto.me.StudentInformationResponse

class StudentApi(
    private val client: HttpClient
) {
    suspend fun getStudentInfo(): StudentInformationResponse{
        return client.get("/api/v1/student/me").body()
    }
}