package org.example.project.presentations.screen.transcript_term

import org.example.project.domain.model.SubjectResultUiModel

data class TranscriptTermState(
    val semesterLabel: String = "",
    val semesterGpa: Double = 0.0,
    val creditsPassed: Int = 0,
    val academicYear: String = "",
    val subjects: List<SubjectResultUiModel> = emptyList(),

    val isRefreshing: Boolean = false
)