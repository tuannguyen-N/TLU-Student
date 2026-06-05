package org.example.project.presentations.screen.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import org.example.project.domain.repository.MessageRepository
import org.example.project.domain.repository.PresenceRepository
import org.example.project.domain.usecase.StudentUseCase

class MessageViewModelFactory(
    private val messageRepository: MessageRepository,
    private val studentUseCase: StudentUseCase,
    private val presenceRepository: PresenceRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        if (modelClass.isAssignableFrom(MessageViewModel::class.java)) {
            val savedStateHandle = extras.createSavedStateHandle()
            return MessageViewModel(savedStateHandle, messageRepository, studentUseCase, presenceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}