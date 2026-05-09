package org.example.project.data.remote.dto.study_program

import kotlinx.serialization.Serializable

@Serializable
data class StudyProgram(
    val studentCode: String,
    val studyProgramCode: String,
    val studyProgramName: String,
    val isPrimary: Boolean,
    val totalCredits: Int,
    val startYear: Int
)