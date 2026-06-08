package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import org.example.project.data.remote.dto.Response
import org.example.project.data.remote.dto.me.SelfUpdateRequest
import org.example.project.data.remote.dto.me.StudentInformationResponse
import org.example.project.data.remote.dto.student_search.StudentInfoResponse
import org.example.project.data.remote.dto.student_search.StudentListResponse

class StudentApi(
    private val client: HttpClient
) {
    suspend fun getStudentInfo(): StudentInformationResponse {
        return client.get("/api/v1/students/me").body()
    }

    suspend fun getStudentInfo(studentCode: String): StudentInfoResponse {
        return client.get("/api/v1/chat/student") {
            parameter("code", studentCode)
        }.body()
    }

    suspend fun searchStudents(
        search: String? = null,
        page: Int = 0,
        size: Int = 10,
        sort: String? = null
    ): StudentListResponse {
        return client.get("/api/v1/chat/list-students") {
            parameter("search", search)
            parameter("page", page)
            parameter("size", size)
            parameter("sort", sort)
        }.body()
    }

    suspend fun updateStudentInfo(request: SelfUpdateRequest): Response {
        return client.post("/api/v1/students/me/update") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun updateAvatar(
        fileName: String,
        fileBytes: ByteArray
    ): Response {
        return client.submitFormWithBinaryData(
            url = "/api/v1/students/me/avatar",
            formData = formData {
                append(
                    key = "file",
                    value = fileBytes,
                    headers = Headers.build {
                        append(HttpHeaders.ContentType, "image/*")
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=\"$fileName\""
                        )
                    }
                )
            }
        ).body()
    }
}