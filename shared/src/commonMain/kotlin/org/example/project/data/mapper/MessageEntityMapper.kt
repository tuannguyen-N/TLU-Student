package org.example.project.data.mapper

import org.example.project.data.local.entity.MessageEntity
import org.example.project.domain.model.Message

fun Message.toEntity(roomId: String): MessageEntity = MessageEntity(
    id = id,
    roomId = roomId,
    senderId = senderId,
    text = text,
    fileUrl = fileUrl,
    fileName = fileName,
    fileSize = fileSize,
    type = type,
    timestamp = timestamp,
    senderType = senderType
)

fun MessageEntity.toMessage(): Message = Message(
    id = id,
    senderId = senderId,
    text = text,
    fileUrl = fileUrl,
    fileName = fileName,
    fileSize = fileSize,
    type = type,
    timestamp = timestamp,
    senderType = senderType
)
