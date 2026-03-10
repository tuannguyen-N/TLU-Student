package org.example.project.domain.model

data class TranscriptState(
    val transcriptUiModel: TranscriptUiModel? = null,
    val gpa: Double = 0.0,
    val totalCredit: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)