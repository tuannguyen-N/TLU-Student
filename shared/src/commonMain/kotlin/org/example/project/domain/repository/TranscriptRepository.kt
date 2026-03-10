package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.remote.api.StudyProgramApi
import org.example.project.data.remote.api.TranscriptApi
import org.example.project.data.remote.dto.transcript.Transcript

class TranscriptRepository(
    private val transcriptApi: TranscriptApi,
    private val studyProgramApi: StudyProgramApi
) {
    private val _transcriptCached = MutableStateFlow<Transcript?>(null)
    val transcriptCached = _transcriptCached.asStateFlow()

    suspend fun getTranscript(): Result<Transcript> {
        return try {
            val studyProgram = studyProgramApi
                .getStudyPrograms()
                .data
                ?.firstOrNull()
                ?: return Result.failure(Exception("Study program not found"))

            val transcript = transcriptApi
                .getTranscript(studyProgram.studyProgramCode)
                .data ?: return Result.failure(Exception("Transcript empty"))
            _transcriptCached.value = transcript
            Result.success(transcript)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}