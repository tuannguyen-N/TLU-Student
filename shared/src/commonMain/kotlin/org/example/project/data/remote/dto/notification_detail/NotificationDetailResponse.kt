package org.example.project.data.remote.dto.notification_detail

import kotlinx.serialization.Serializable

@Serializable
data class NotificationDetailResponse(
    val code: Int,
    val data: NotificationDetailData?,
    val message: String
)