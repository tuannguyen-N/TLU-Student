package org.example.project.presentations.screen.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.ConversationUiState
import org.example.project.domain.repository.MessageRepository

class MessagesViewModel(
    private val messageRepository: MessageRepository
) : ViewModel() {

    private val _conversations = MutableStateFlow<List<ConversationUiState>>(emptyList())
    val conversations = _conversations.asStateFlow()

    init {
        observeConversations()
    }

    private fun observeConversations() {
        viewModelScope.launch {
            messageRepository
                .observeConversations("a45044")
                .collect {
                    _conversations.value = it
                }
        }
    }
}