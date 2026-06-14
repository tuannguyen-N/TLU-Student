package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.example.project.data.remote.dto.semester.StudentSemestersResponse
import org.example.project.data.remote.dto.semester_preiod.SemesterPeriodResponse

class SemesterApi(
    private val client: HttpClient
) {
    suspend fun getSemesters(): StudentSemestersResponse {
        return client.get("/api/v1/semester/student").body()
    }

    suspend fun getSemesterPeriod(studyProgramCode: String): SemesterPeriodResponse {
        return client.get("/api/v1/student/enrollment/period"){
            parameter("studyProgramCode", studyProgramCode)
        }.body()
    }
}