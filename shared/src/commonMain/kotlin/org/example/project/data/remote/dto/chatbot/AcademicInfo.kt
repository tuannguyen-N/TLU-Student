package org.example.project.data.remote.dto.chatbot

import kotlinx.serialization.Serializable

@Serializable
data class AcademicInfo(
    val endYear: Int,
    val facultyCode: String,
    val majorCode: String,
    val majorName: String,
    val startYear: Int,
    val studyProgramCode: String
)