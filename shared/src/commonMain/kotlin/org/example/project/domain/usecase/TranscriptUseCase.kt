package org.example.project.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.mapper.TranscriptMapper.toUiModel
import org.example.project.data.remote.dto.transcript.Transcript
import org.example.project.domain.model.TranscriptUiModel
import org.example.project.domain.repository.TranscriptRepository

class TranscriptUseCase(
    private val transcriptRepository: TranscriptRepository
) {
    val transcriptCached: Flow<TranscriptUiModel?> = transcriptRepository.transcriptCached.map {
        it?.toUiModel()
    }

    suspend fun getTranscript(): Result<Transcript> {
        return transcriptRepository.getTranscript()
    }
}