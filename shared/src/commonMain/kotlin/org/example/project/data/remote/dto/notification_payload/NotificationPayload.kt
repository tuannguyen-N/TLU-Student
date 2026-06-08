package org.example.project.data.remote.dto.notification_payload

import kotlinx.serialization.Serializable

@Serializable
data class NotificationPayload(
    val id: Int,
    val title: String,
    val content: String,
    val createdBy: String?,
    val targetType: String,
    val isImportant: Boolean,
    val referenceType: String?,
    val deadLine: String?,
    val createdAt: String,
    val isRead: Boolean
)