package org.example.project.data.remote.dto.semester

import kotlinx.serialization.Serializable

@Serializable
data class StudentSemestersResponse(
    val code: Int,
    val data: List<Semester>?,
    val message: String
)