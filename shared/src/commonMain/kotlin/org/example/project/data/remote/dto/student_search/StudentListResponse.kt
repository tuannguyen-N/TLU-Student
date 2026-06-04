package org.example.project.data.remote.dto.student_search

import kotlinx.serialization.Serializable

@Serializable
data class StudentListResponse(
    val code: Int,
    val message: String,
    val data: StudentPageData?
)