package org.example.project.data.remote.dto.transcript

import kotlinx.serialization.Serializable

@Serializable
data class AcademicResultResponse(
    val code: Int,
    val message: String,
    val data: AcademicResultData?
)