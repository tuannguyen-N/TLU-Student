package org.example.project.domain.model

data class AcademicYearGroup(
    val academicYear: String,
    val semesters: List<SemesterUiModel>
)