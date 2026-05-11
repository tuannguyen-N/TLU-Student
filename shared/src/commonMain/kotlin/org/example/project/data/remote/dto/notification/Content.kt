package org.example.project.data.remote.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class Content(
    val content: String,
    val createdAt: String,
    val createdBy: String?,
    val deadLine: String?,
    val id: Int,
    val isRead: Boolean,
    val targetType: String,
    val title: String,
    val isImportant: Boolean,
    val referenceType: String?
)