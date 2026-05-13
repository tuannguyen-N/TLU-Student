package org.example.project.data.remote.dto.application_history

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationHistoryResponse(
    val code: Int,
    val data: List<ApplicationHistoryData>?,
    val message: String
)