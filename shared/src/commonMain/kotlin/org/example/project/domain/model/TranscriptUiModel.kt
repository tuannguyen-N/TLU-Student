package org.example.project.domain.model

data class TranscriptUiModel(
    val cumulativeGpa: Double,
    val totalCreditsPassed: Int,
    val academicYearGroups: List<AcademicYearGroup>
)