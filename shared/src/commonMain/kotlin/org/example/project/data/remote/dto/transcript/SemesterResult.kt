package org.example.project.data.remote.dto.transcript

import kotlinx.serialization.Serializable

@Serializable
data class SemesterResult(
    val semester: String,
    val subjectResults: List<SubjectResult>,
    val semesterSummary: SemesterSummary
)