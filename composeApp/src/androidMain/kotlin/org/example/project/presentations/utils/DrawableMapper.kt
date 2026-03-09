package org.example.project.presentations.utils

import org.example.project.R
import org.example.project.domain.model.FeatureType
import org.example.project.domain.model.Notification
import org.example.project.domain.model.NotificationType

fun FeatureType.toDrawable(): Int {
    return when (this) {
        FeatureType.UPCOMING -> R.drawable.ic_launcher_background
        FeatureType.FEEDBACK -> R.drawable.ic_feedback
    }
}

fun Notification.avatarRes(): Int {
    return when (this.type) {
        NotificationType.SCHOOL -> R.drawable.icon_avatar_school
        else -> R.drawable.ic_launcher_background
    }
}

fun Notification.iconRes(): Int {
    return when (this.type) {
        NotificationType.SCHOOL -> R.drawable.icon_school_notification
        NotificationType.DEPARTMENT -> R.drawable.icon_deparment_notification
        else -> R.drawable.icon_teacher_notification
    }
}