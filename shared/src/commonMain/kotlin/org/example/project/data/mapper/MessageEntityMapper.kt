package org.example.project.data.mapper

import org.example.project.data.local.entity.MessageEntity
import org.example.project.domain.model.Message
import org.example.project.domain.model.MessageStatus

fun Message.toEntity(roomId: String, status: MessageStatus? = null): MessageEntity = MessageEntity(
    id = id,
    roomId = roomId,
    senderId = senderId,
    text = text,
    fileUrl = fileUrl,
    fileName = fileName,
    fileSize = fileSize,
    type = type,
    timestamp = timestamp,
    senderType = senderType,
    status = status
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
    senderType = senderType,
    status = status ?: MessageStatus.SENT
)
