package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.data.remote.dto.study_program.StudyProgramResponse

class StudyProgramApi(
    private val client: HttpClient
) {
    suspend fun getStudyPrograms(): StudyProgramResponse {
        return client.get("/api/v1/study-programs/studyProgramCode").body()
    }
}