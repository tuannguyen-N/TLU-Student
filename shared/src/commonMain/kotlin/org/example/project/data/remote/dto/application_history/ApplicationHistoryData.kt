package org.example.project.data.remote.dto.application_history

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationHistoryData(
    val id: Int,
    val createdAt: String,
    val status: String,
    val typeName: String
)