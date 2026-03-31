package org.example.project.data.remote.dto.transcript

import kotlinx.serialization.Serializable

@Serializable
data class AcademicResultData(
    val studyProgram: String,
    val semesterResults: List<SemesterResult>
)