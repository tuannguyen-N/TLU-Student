package org.example.project.data.mapper

import org.example.project.domain.model.AlertActionItem
import org.example.project.domain.model.AlertPriority
import org.example.project.domain.model.AlertUiModel
import org.example.project.domain.model.NotificationReferenceType
import org.example.project.domain.model.NotificationSeverity

fun AlertUiModel.toAlertActionItem(): AlertActionItem {
    val tag = when (severity) {
        NotificationSeverity.WARNING -> "CẦN THỰC HIỆN"
        NotificationSeverity.UPCOMING -> "SẮP ĐẾN HẠN"
        NotificationSeverity.NORMAL -> "THÔNG BÁO"
        NotificationSeverity.OVERDUE -> "QUÁ HẠN"
        NotificationSeverity.COMPLETED -> "HOÀN THÀNH"
    }

    val priority = when (severity) {
        NotificationSeverity.WARNING -> AlertPriority.URGENT
        NotificationSeverity.UPCOMING -> AlertPriority.NEW
        NotificationSeverity.NORMAL -> AlertPriority.INFO
        NotificationSeverity.OVERDUE -> AlertPriority.OVERDUE
        NotificationSeverity.COMPLETED -> AlertPriority.COMPLETED
    }

//    val iconRes = when (notificationReferenceType) {
//        NotificationReferenceType.TUITION -> R.drawable.icon_caution
//        NotificationReferenceType.EXAM_SCHEDULE -> R.drawable.icon_upcoming
//    }
//
//    val iconTint = when (severity) {
//        NotificationSeverity.WARNING -> Color(0xFFE53935)
//        NotificationSeverity.UPCOMING -> Color(0xFF3949AB)
//        NotificationSeverity.NORMAL -> Color(0xFF757575)
//        NotificationSeverity.OVERDUE -> Color(0xFFB71C1C)
//        NotificationSeverity.COMPLETED -> Color(0xFF2E7D32)
//    }
//
//    val iconBackground = when (severity) {
//        NotificationSeverity.WARNING -> Color(0xFFFFEBEE)
//        NotificationSeverity.UPCOMING -> Color(0xFFE8EAF6)
//        NotificationSeverity.NORMAL -> Color(0xFFF5F5F5)
//        NotificationSeverity.OVERDUE -> Color(0xFFFFCDD2)
//        NotificationSeverity.COMPLETED -> Color(0xFFE8F5E9)
//    }

    val actionLabel = when (severity) {
        NotificationSeverity.WARNING -> "Thực hiện ngay →"
        NotificationSeverity.UPCOMING -> "Thực hiện ngay"
        NotificationSeverity.NORMAL -> "Xem chi tiết"
        NotificationSeverity.OVERDUE -> "Xem chi tiết →"
        NotificationSeverity.COMPLETED -> ""
    }

    return AlertActionItem(
        tag = tag,
        priority = priority,
        deadline = deadline,
        title = title,
        description = content,
        referenceType = notificationReferenceType,
        actionLabel = actionLabel,
    )
}