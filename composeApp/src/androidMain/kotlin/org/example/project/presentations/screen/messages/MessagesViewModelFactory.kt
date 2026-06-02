package org.example.project.presentations.screen.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.MessageRepository

class MessagesViewModelFactory(
    private val messageRepository: MessageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
            return MessagesViewModel(messageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}