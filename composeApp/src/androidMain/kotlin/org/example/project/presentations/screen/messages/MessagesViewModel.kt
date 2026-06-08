package org.example.project.presentations.screen.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.example.project.domain.model.ConversationUiState
import org.example.project.domain.model.UserUiModel
import org.example.project.domain.repository.MessageRepository
import org.example.project.domain.repository.PresenceRepository
import org.example.project.domain.repository.UserRepository
import org.example.project.domain.usecase.StudentUseCase

class MessagesViewModel(
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
    val studentUseCase: StudentUseCase,
    presenceRepository: PresenceRepository
) : ViewModel() {
    val conversations = combine(
        studentUseCase.studentInfo.filterNotNull(),
        presenceRepository.observeAllPresence()
    ) { student, presenceMap ->
        Pair(student, presenceMap)
    }
        .flatMapLatest { (student, presenceMap) ->
            messageRepository.observeConversations(
                student.studentCode.lowercase()
            )
                .map { conversations ->
                    conversations.map { conversation ->
                        val presence = presenceMap[conversation.studentId]

                        ConversationUiState(
                            roomId = conversation.roomId,
                            studentId = conversation.studentId,
                            chatName = conversation.chatName,
                            avatarUrl = conversation.avatarUrl,
                            unreadCount = conversation.unreadCount,
                            lastMessageText = conversation.lastMessageText,
                            lastMessageTimeFormatted = conversation.lastMessageTimeFormatted,
                            lastMessageType = conversation.lastMessageType,
                            isLastMessageFromMe = conversation.isLastMessageFromMe,
                            isOnline = presence?.isOnline ?: false,
                            lastSeen = presence?.lastSeen ?: 0L
                        )
                    }
                }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

    val users = combine(
        studentUseCase.studentInfo.filterNotNull(),
        presenceRepository.observeAllPresence()
    ) { student, presenceMap ->
        Pair(student, presenceMap)
    }
        .flatMapLatest { (student, presenceMap) ->
            userRepository.observeUsers(
                size = 10,
                excludeUserId = student.studentCode.lowercase()
            )
                .map { users ->
                    users.map { user ->
                        val presence = presenceMap[user.id]
                        UserUiModel(
                            studentCode = user.id,
                            name = user.name,
                            avatarUrl = user.avatarUrl,
                            isOnline = presence?.isOnline ?: false,
                            lastSeen = presence?.lastSeen ?: 0L
                        )
                    }
                }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )
}