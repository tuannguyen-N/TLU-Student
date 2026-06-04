package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.example.project.data.remote.dto.me.StudentInformationResponse
import org.example.project.data.remote.dto.student_search.StudentListResponse

class StudentApi(
    private val client: HttpClient
) {

    suspend fun getStudentInfo(): StudentInformationResponse {
        return client.get("/api/v1/students/me").body()
    }

    suspend fun searchStudents(
        name: String? = null,
        studentCode: String? = null,
        page: Int = 0,
        size: Int = 10,
        sort: String? = null
    ): StudentListResponse {
        return client.get("/api/v1/students/list-students") {
            parameter("name", name)
            parameter("studentCode", studentCode)
            parameter("page", page)
            parameter("size", size)
            parameter("sort", sort)
        }.body()
    }
}