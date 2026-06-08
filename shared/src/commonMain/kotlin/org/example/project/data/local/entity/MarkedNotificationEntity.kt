package org.example.project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marked_notifications")
data class MarkedNotificationEntity(
    @PrimaryKey
    val id: Int,
//    val title: String,
//    val content: String,
//    val sender: NotificationSender,
//    val deadline: String?,
//    val isRead: Boolean,
//    val createdAt: String,
//    val createdTime: String,
//    val createdDate: String,
//    val createdAgo: String
)