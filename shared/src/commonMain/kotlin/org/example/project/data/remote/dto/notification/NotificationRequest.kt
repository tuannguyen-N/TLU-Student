package org.example.project.data.remote.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationRequest(
    val oauthUserId: Int,
    val facultyId: Int,
    val studentClassId: Int
)