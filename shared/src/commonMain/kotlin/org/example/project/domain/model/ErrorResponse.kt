package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: Int,
    val message: String
)
