package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.model.ConversationUiState
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.model.SenderType

interface MessageRepository {
    fun observeConversations(
        currentStudentId: String
    ): Flow<List<ConversationUiState>>

    fun observeMessages(
        roomId: String,
        currentUserId: String
    ): Flow<List<MessageUiState>>

    suspend fun <T, K : Any> loadOlderMessages(
        roomId: String,
        currentUserId: String,
        lastDocument: T?
    ): K

    suspend fun markConversationAsRead(
        roomId: String,
        currentUserId: String
    )

    suspend fun sendMessage(
        roomId: String,
        currentUserId: String,
        senderType: SenderType,
        message: String
    )

    suspend fun sendImageMessage(
        roomId: String,
        senderId: String,
        imageBytes: ByteArray,
        caption: String?
    )

    suspend fun sendVideoMessage(
        roomId: String,
        senderId: String,
        videoBytes: ByteArray,
        caption: String?
    )

    suspend fun sendFileMessage(
        roomId: String,
        senderId: String,
        fileBytes: ByteArray,
        fileName: String,
        fileSize: String,
        caption: String?
    )

    suspend fun preloadRecentMessages(
        roomIds: List<String>,
        currentUserId: String,
        limit: Int = 3
    )
}