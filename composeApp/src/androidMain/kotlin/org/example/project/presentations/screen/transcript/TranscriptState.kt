package org.example.project.presentations.screen.transcript

import org.example.project.domain.model.TranscriptUiModel

data class TranscriptState(
    val transcriptUiModel: TranscriptUiModel? = null,
    val gpa: Double = 0.0,
    val totalCredit: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)