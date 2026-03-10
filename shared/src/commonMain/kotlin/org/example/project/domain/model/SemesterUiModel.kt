package org.example.project.domain.model

data class SemesterUiModel(
    val semesterLabel: String,
    val semesterGpa: Double,
    val creditsPassed: Int,
    val academicYear: String,
    val subjects: List<SubjectResultUiModel>
)