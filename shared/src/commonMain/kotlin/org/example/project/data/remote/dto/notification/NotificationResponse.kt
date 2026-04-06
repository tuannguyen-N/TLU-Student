package org.example.project.data.remote.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
    val code: Int,
    val data: NotificationData?,
    val message: String
)