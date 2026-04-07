package org.example.project.data.remote.dto.notification_prepare

import kotlinx.serialization.Serializable

@Serializable
data class PrepareNotificationResponse(
    val code: Int,
    val `data`: PrepareNotificationData?,
    val message: String
)