package org.example.project.data.mapper

import org.example.project.data.remote.dto.notification.Content
import org.example.project.data.remote.dto.notification.NotificationData
import org.example.project.domain.model.NotificationSender
import org.example.project.domain.model.NotificationSender.FACULTY
import org.example.project.domain.model.NotificationSender.LECTURER
import org.example.project.domain.model.NotificationSender.SYSTEM
import org.example.project.domain.model.NotificationUiModel

fun NotificationData.toListNotificationUiModel(): List<NotificationUiModel> {
    return content.map {
        it.toUiModel()
    }
}

fun Content.toUiModel(): NotificationUiModel = NotificationUiModel(
    id = id,
    title = title,
    content = content,
    sender = createdBy.toNotificationSender(),
    deadline = deadLine,
    isRead = isRead,
    createdAt = createdAt,
    createdTime = createdAt.toCreatedTime(),
    createdDate = createdAt.toCreatedDate(),
    createdAgo = createdAt.toCreatedAgo()
)

fun String.toNotificationSender(): NotificationSender {
    return when (this) {
        "FACULTY" -> FACULTY
        "LECTURER" -> LECTURER
        else -> SYSTEM
    }
}