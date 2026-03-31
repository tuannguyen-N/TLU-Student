package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.remote.api.StudyProgramApi
import org.example.project.data.remote.api.TranscriptApi
import org.example.project.data.remote.dto.transcript.AcademicResultData
import org.example.project.domain.model.ApiResult

class TranscriptRepository(
    private val transcriptApi: TranscriptApi,
    private val studyProgramApi: StudyProgramApi
) {
    private val _transcriptCached = MutableStateFlow<AcademicResultData?>(null)
    val transcriptCached = _transcriptCached.asStateFlow()

    suspend fun getTranscript(): ApiResult<AcademicResultData> {
        return try {
            val studyProgram = studyProgramApi
                .getStudyPrograms()
                .data
                ?.firstOrNull()
                ?: return ApiResult.Failure(message = "Study program not found")

            val transcript = transcriptApi
                .getTranscript(studyProgram.studyProgramCode)
                .data ?: return ApiResult.Failure(message = "Transcript empty")
            _transcriptCached.value = transcript
            ApiResult.Success(transcript)

        } catch (e: Exception) {
            ApiResult.Failure(message = e.message, cause = e)
        }
    }
}