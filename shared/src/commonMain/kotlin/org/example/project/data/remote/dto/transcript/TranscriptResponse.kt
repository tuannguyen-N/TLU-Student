package org.example.project.data.remote.dto.transcript

import kotlinx.serialization.Serializable

@Serializable
data class TranscriptResponse(
    val code: Int,
    val message: String,
    val data: Data?
)