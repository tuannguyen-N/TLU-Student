package org.example.project.domain.model

data class TranscriptTermState(
    val semesterLabel: String = "",
    val semesterGpa: Double = 0.0,
    val creditsPassed: Int = 0,
    val academicYear: String = "",
    val subjects: List<SubjectResultUiModel> = emptyList()
)