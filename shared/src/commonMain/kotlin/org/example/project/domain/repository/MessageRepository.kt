package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.model.ConversationUiState
import org.example.project.domain.model.MessageUiState

interface MessageRepository {
    fun observeConversations(
        currentStudentId: String
    ): Flow<List<ConversationUiState>>

    fun observeMessages(
        roomId: String,
        currentUserId: String
    ): Flow<List<MessageUiState>>

    fun observeUserOnlineStatus(
        userId: String
    ): Flow<Boolean>

    suspend fun markConversationAsRead(
        roomId: String,
        currentUserId: String
    )

    suspend fun sendMessage(
        roomId: String,
        currentUserId: String,
        message: String
    )
}