package org.example.project.data.remote.dto.chatbot

import kotlinx.serialization.Serializable

@Serializable
data class ChatbotContextData(
    val academicInfo: List<AcademicInfo>,
    val dateOfBirth: String,
    val gender: String,
    val semesters: List<Semester>,
    val studentCode: String,
    val studentName: String
)