package org.example.project.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.mapper.TranscriptMapper.toUiModel
import org.example.project.data.remote.dto.study_program.StudyProgram
import org.example.project.data.remote.dto.transcript.AcademicResultData
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.ExportedFile
import org.example.project.domain.model.TranscriptUiModel
import org.example.project.domain.repository.TranscriptRepository

class TranscriptUseCase(
    private val transcriptRepository: TranscriptRepository
) {
    val transcriptCached: Flow<TranscriptUiModel?> = transcriptRepository.transcriptCached.map {
        it?.toUiModel()
    }

    val studyProgram: Flow<StudyProgram?> = transcriptRepository.studyProgram

    suspend fun getTranscript(forceRefresh: Boolean = false): AppResult<AcademicResultData> {
        return transcriptRepository.getTranscript(forceRefresh)
    }

    suspend fun exportTranscript(): AppResult<ExportedFile> {
        return transcriptRepository.exportTranscript()
    }
}