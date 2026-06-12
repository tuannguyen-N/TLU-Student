package org.example.project.presentations.screen.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import org.example.project.domain.repository.ChatRepository
import org.example.project.domain.repository.MessageRepository
import org.example.project.domain.repository.PresenceRepository
import org.example.project.domain.usecase.StudentUseCase
import org.example.project.domain.usecase.SummaryUseCase

class MessageViewModelFactory(
    private val messageRepository: MessageRepository,
    private val studentUseCase: StudentUseCase,
    private val presenceRepository: PresenceRepository,
    private val chatRepository: ChatRepository,
    private val summaryUseCase: SummaryUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        if (modelClass.isAssignableFrom(MessageViewModel::class.java)) {
            val savedStateHandle = extras.createSavedStateHandle()
            return MessageViewModel(
                savedStateHandle,
                messageRepository,
                studentUseCase,
                presenceRepository,
                chatRepository,
                summaryUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}