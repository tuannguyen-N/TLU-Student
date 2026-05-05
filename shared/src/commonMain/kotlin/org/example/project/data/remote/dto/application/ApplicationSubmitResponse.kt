package org.example.project.data.remote.dto.application

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationSubmitResponse(
    val code: Int,
    val data: List<String>?,
    val message: String
)