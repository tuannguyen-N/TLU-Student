package org.example.project.presentations.screen.message

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.MessageStatus
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.model.User
import org.example.project.domain.repository.MessageRepository
import org.example.project.domain.usecase.StudentUseCase
import java.util.UUID

class MessageViewModel(
    savedStateHandle: SavedStateHandle,
    private val messageRepository: MessageRepository,
    private val studentUseCase: StudentUseCase
) : ViewModel() {
    private val roomId = savedStateHandle.get<String>("roomId") ?: ""
    private val chatUserId = savedStateHandle.get<String>("studentId") ?: ""
    private val chatUserName = savedStateHandle.get<String>("chatName") ?: ""
    val studentId = studentUseCase.studentInfo.value?.studentCode?.lowercase()
    private val pendingMessages = MutableStateFlow<List<MessageUiState>>(emptyList())

    private val _chatUser = MutableStateFlow(
        User(
            id = chatUserId,
            name = chatUserName,
            avatarUrl = "",
            isOnline = false
        )
    )

    val chatUser = _chatUser.asStateFlow()

    private val remoteMessages = messageRepository
        .observeMessages(roomId, studentId!!)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000), replay = 1)

    val messages = combine(remoteMessages, pendingMessages) { remote, pending ->
        val latestRemoteTime = remote.maxOfOrNull { it.timestamp } ?: 0L
        val stillPending = pending.filter { it.timestamp > latestRemoteTime }
        remote + stillPending
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _uiState = MutableStateFlow(MessageState())
    val uiState = _uiState.asStateFlow()

    init {
        markAsRead()
        observeUserOnlineStatus()
        observeAndMarkNewMessages()
    }

    private fun observeAndMarkNewMessages() {
        viewModelScope.launch {
            remoteMessages
                .distinctUntilChangedBy { messages ->
                    messages.maxOfOrNull { it.timestamp } ?: 0L
                }
                .drop(1)
                .filter { messages ->
                    messages.lastOrNull()?.isMe == false
                }
                .collect {
                    messageRepository.markConversationAsRead(roomId, studentId!!)
                }
        }
    }

    private fun observeUserOnlineStatus() {
        viewModelScope.launch {
            messageRepository.observeUserOnlineStatus(chatUserId)
                .collect { isOnline ->
                    _chatUser.update { user ->
                        user.copy(isOnline = isOnline)
                    }
                }
        }
    }

    private fun markAsRead() {
        viewModelScope.launch {
            messageRepository.markConversationAsRead(roomId, studentId!!)
        }
    }

    fun onMessageChange(message: String) {
        updateState { copy(message = message) }
    }

    fun onSend() {
        val text = uiState.value.message
        if (text.isBlank()) return
        val tempId = UUID.randomUUID().toString()
        val pendingMessage = MessageUiState(
            id = tempId,
            senderId = studentId!!,
            text = text,
            type = "TEXT",
            timestamp = System.currentTimeMillis(),
            isMe = true,
            status = MessageStatus.SENDING
        )

        pendingMessages.update { it + pendingMessage }

        updateState {
            copy(message = "")
        }

        viewModelScope.launch {
            messageRepository.sendMessage(
                roomId,
                studentId,
                text
            )
        }
    }

    private fun updateState(block: MessageState.() -> MessageState) {
        _uiState.value = _uiState.value.block()
    }
}