package org.example.project.data.remote.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class MarkReadNotificationRequest(
    val notificationIds: List<Int>
)