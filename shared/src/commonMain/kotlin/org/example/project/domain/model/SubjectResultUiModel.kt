package org.example.project.domain.model

data class SubjectResultUiModel(
    val subjectName: String,
    val subjectCode: String,
    val credits: Int,
    val score10: Double,
    val score4: Double,
    val letterGrade: String,
    val isPass: Boolean
)