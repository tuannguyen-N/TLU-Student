package org.example.project.domain.model

data class SubjectResultUiModel(
    val subjectName: String,
    val subjectCode: String,
    val credits: Int,
    val attendanceScore: Double,
    val midtermScore: Double,
    val finalScore: Double,
    val score10: Double,
    val score4: Double,
    val letterGrade: String,
    val isPass: Boolean
)