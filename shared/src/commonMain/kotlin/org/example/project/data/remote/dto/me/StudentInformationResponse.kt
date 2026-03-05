package org.example.project.data.remote.dto.me

import kotlinx.serialization.Serializable

@Serializable
data class StudentInformationResponse(
    val code: Int,
    val message: String,
    val data: StudentInformation?
)