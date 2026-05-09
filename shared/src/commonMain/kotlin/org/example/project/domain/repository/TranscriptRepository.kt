package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.data.cache.CacheManager
import org.example.project.data.remote.api.StudyProgramApi
import org.example.project.data.remote.api.TranscriptApi
import org.example.project.data.remote.dto.study_program.StudyProgram
import org.example.project.data.remote.dto.transcript.AcademicResultData
import org.example.project.domain.model.AppResult
import kotlin.time.Duration.Companion.minutes

class TranscriptRepository(
    private val transcriptApi: TranscriptApi,
    private val studyProgramApi: StudyProgramApi
) {
    private val transcriptCache = CacheManager<String, AcademicResultData>(3.minutes)

    private val _transcriptCached = MutableStateFlow<AcademicResultData?>(null)
    val transcriptCached = _transcriptCached.asStateFlow()

    private val _studyProgram = MutableStateFlow<StudyProgram?>(null)
    val studyProgram = _studyProgram.asStateFlow()

    suspend fun getTranscript(
        forceRefresh: Boolean = false
    ): AppResult<AcademicResultData>{
        return try {
            val data = transcriptCache.getOrFetch(
                key = "transcript",
                forceRefresh = forceRefresh
            ){
                val studyProgram = studyProgramApi.getStudyPrograms().data
                    ?.firstOrNull()
                    ?: throw Exception("Study program not found")

                _studyProgram.update { studyProgram }

                transcriptApi.getTranscript(studyProgram.studyProgramCode)
                    .data ?: throw Exception("Transcript empty")
            }

            _transcriptCached.update { data }
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }
}