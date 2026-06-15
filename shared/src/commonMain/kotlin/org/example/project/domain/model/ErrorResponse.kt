package org.example.project.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ErrorResponse(
    val code: Int,
    val message: String,
    val data: JsonElement? = null
)