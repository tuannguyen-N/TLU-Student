package org.example.project.data.remote.dto.student_search

import kotlinx.serialization.Serializable

@Serializable
data class StudentInfoResponse(
    val code: Int,
    val message: String,
    val data: StudentData?
)

@Serializable
data class StudentData(
    val studentCode: String,
    val fullName: String,
    val classCode: String,
    val majorName: String,
    val position: String
)