package org.example.project.data.mapper

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.datetime.until
import org.example.project.data.local.entity.NotificationEntity
import org.example.project.data.remote.dto.notification.Content
import org.example.project.data.remote.dto.notification.NotificationData
import org.example.project.domain.model.AlertUiModel
import org.example.project.domain.model.NotificationReferenceType
import org.example.project.domain.model.NotificationSender
import org.example.project.domain.model.NotificationSender.FACULTY
import org.example.project.domain.model.NotificationSender.LECTURER
import org.example.project.domain.model.NotificationSender.SYSTEM
import org.example.project.domain.model.NotificationSeverity
import org.example.project.domain.model.NotificationUiModel
import kotlin.time.Clock

fun NotificationData.toListNotificationUiModel(): List<NotificationUiModel> {
    return content.map {
        it.toUiModel()
    }
}

fun Content.toUiModel(): NotificationUiModel = NotificationUiModel(
    id = id,
    title = title,
    content = content,
    sender = createdBy?.toNotificationSender() ?: SYSTEM,
    deadline = deadLine,
    isRead = isRead,
    createdAt = createdAt,
    createdTime = createdAt.toCreatedTime(),
    createdDate = createdAt.toCreatedDate(),
    createdAgo = createdAt.toCreatedAgo(),
    referenceType = referenceType?.toNotificationReferenceType()
)

fun List<NotificationUiModel>.toAlertUiModels(): List<AlertUiModel> {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    return mapNotNull { notification ->
        val deadline = notification.deadline ?: return@mapNotNull null
        val referenceType = notification.referenceType ?: return@mapNotNull null

        val deadlineDate = runCatching {
            LocalDate.parse(deadline)
        }.getOrNull() ?: return@mapNotNull null

        val daysUntil = today.until(deadlineDate, DateTimeUnit.DAY)

        val severity = when {
            daysUntil < 0    -> return@mapNotNull null
            daysUntil <= 3   -> NotificationSeverity.WARNING
            daysUntil <= 100 -> NotificationSeverity.UPCOMING
            else             -> NotificationSeverity.NORMAL
        }

        val formattedDeadline = "Hạn: ${deadlineDate.dayOfMonth.toString().padStart(2, '0')}/${deadlineDate.monthNumber.toString().padStart(2, '0')}"

        AlertUiModel(
            title = notification.title,
            content = notification.content,
            severity = severity,
            notificationReferenceType = referenceType,
            deadline = formattedDeadline,
            daysUntil = daysUntil
        )
    }
}

private fun String.toNotificationReferenceType(): NotificationReferenceType {
    return when (this) {
        "EXAM_SCHEDULE" -> NotificationReferenceType.EXAM_SCHEDULE
        "TUITION" -> NotificationReferenceType.TUITION
        else -> NotificationReferenceType.TUITION
    }
}

fun String.toNotificationSender(): NotificationSender {
    return when (this) {
        "FACULTY" -> FACULTY
        "LECTURER" -> LECTURER
        else -> SYSTEM
    }
}

fun NotificationUiModel.toEntity(): NotificationEntity {
    return NotificationEntity(
        id = id,
//        title = title,
//        content = content,
//        sender = sender,
//        deadline = deadline,
//        isRead = isRead,
//        createdAt = createdAt,
//        createdTime = createdTime,
//        createdDate = createdDate,
//        createdAgo = createdAgo
    )
}

//fun NotificationEntity.toUiModel(): NotificationUiModel {
//    return NotificationUiModel(
//        id = id,
//        title = title,
//        content = content,
//        sender = sender,
//        deadline = deadline,
//        isRead = isRead,
//        createdAt = createdAt,
//        createdTime = createdTime,
//        createdDate = createdDate,
//        createdAgo = createdAgo
//    )
//}