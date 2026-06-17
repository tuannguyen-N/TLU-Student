package org.example.project.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import org.example.project.domain.model.MessageStatus

@Entity(
    tableName = "chat_messages",
    primaryKeys = ["id"],
    indices = [Index(value = ["roomId", "timestamp"])]
)
data class MessageEntity(
    val id: String,
    val roomId: String,
    val senderId: String,
    val text: String?,
    val fileUrl: String?,
    val fileName: String?,
    val status: MessageStatus?,
    val fileSize: String?,
    val type: String,
    val timestamp: Long,
    val senderType: String
)
