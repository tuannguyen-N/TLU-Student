package org.example.project.data.remote.dto.student_class

import kotlinx.serialization.Serializable

@Serializable
data class StudentClassInfoResponse(
    val code: Int,
    val message: String,
    val data: StudentClassInfoData?
)