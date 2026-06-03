package org.example.project.presentations.screen.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import org.example.project.domain.repository.MessageRepository
import org.example.project.domain.usecase.StudentUseCase

class MessagesViewModel(
    private val messageRepository: MessageRepository,
    private val studentUseCase: StudentUseCase
) : ViewModel() {
    val conversations = studentUseCase.studentInfo
        .filterNotNull()
        .flatMapLatest { student ->
            messageRepository.observeConversations(
                student.studentCode.lowercase()
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}