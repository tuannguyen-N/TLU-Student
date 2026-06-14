package org.example.project.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Response(
    val code: Int,
    val message: String,
    val data: JsonElement? = null
)