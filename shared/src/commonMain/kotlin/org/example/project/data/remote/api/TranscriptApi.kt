package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.example.project.data.remote.dto.transcript.AcademicResultResponse

class TranscriptApi(
    private val client: HttpClient
) {
    suspend fun getTranscript(studyProgram: String): AcademicResultResponse{
        return client.get("/api/v1/student/marks"){
            parameter("ctdt", studyProgram)
        }.body()
    }
}