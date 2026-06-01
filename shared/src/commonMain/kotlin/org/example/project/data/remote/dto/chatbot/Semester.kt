package org.example.project.data.remote.dto.chatbot

import kotlinx.serialization.Serializable

@Serializable
data class Semester(
    val academicYears: String,
    val endDate: String,
    val id: Int,
    val semesterName: String,
    val semesterNumber: Int,
    val startDate: String
)