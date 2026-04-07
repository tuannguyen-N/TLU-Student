package org.example.project.data.remote.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class MarkReadNotificationResponse(
    val code: Int,
    val message: String,
    val data: String?
)
