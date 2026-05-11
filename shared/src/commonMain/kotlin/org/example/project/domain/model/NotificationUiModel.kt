package org.example.project.domain.model

data class NotificationUiModel(
    val id: Int,
    val title: String,
    val content: String,
    val sender: NotificationSender,
    val deadline: String?,
    val isRead: Boolean,
    val createdAt: String,
    val createdTime: String,
    val createdDate: String,
    val createdAgo: String,
    val referenceType: NotificationReferenceType?
)