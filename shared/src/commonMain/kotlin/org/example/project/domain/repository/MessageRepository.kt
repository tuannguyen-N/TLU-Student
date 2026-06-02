package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.model.ConversationUiState

interface MessageRepository {
    fun observeConversations(
        currentStudentId: String
    ): Flow<List<ConversationUiState>>
}