package org.example.project.data.remote.dto.student_search

import kotlinx.serialization.Serializable

@Serializable
data class StudentPageData(
    val content: List<StudentSummary>,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
    val first: Boolean,
    val last: Boolean
)