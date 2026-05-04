package org.example.project.data.remote.dto.notification_detail

import kotlinx.serialization.Serializable

@Serializable
data class NotificationDetailData(
    val content: String,
    val createdAt: String,
    val createdBy: String?,
    val deadLine: String?,
    val targetType: String,
    val title: String
)