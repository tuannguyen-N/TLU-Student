package org.example.project.data.remote.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationData(
    val content: List<NotificationContent>,
    val first: Boolean,
    val last: Boolean,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)