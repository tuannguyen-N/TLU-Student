package org.example.project.presentations.screen.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.MessageRepository
import org.example.project.domain.repository.UserRepository
import org.example.project.domain.usecase.StudentUseCase

class MessagesViewModelFactory(
    private val messageRepository: MessageRepository,
    private val studentUseCase: StudentUseCase,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
            return MessagesViewModel(messageRepository, userRepository, studentUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}