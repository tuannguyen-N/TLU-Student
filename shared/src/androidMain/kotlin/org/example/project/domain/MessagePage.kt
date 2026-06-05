package org.example.project.domain

import com.google.firebase.firestore.DocumentSnapshot
import org.example.project.domain.model.MessageUiState

data class MessagePage(
    val messages: List<MessageUiState>,
    val lastDocument: DocumentSnapshot?,
    val hasMore: Boolean
)