package org.example.project.data.remote.dto.study_program

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val id: Int,
    val isPrimary: Boolean,
    val studentCode: String,
    val studyProgramCode: String,
    val studyProgramName: String
)