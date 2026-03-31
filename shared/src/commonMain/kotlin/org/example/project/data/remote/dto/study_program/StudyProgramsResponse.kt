package org.example.project.data.remote.dto.study_program

import kotlinx.serialization.Serializable

@Serializable
data class StudyProgramsResponse(
    val code: Int,
    val message: String,
    val data: List<StudyProgram>?
)