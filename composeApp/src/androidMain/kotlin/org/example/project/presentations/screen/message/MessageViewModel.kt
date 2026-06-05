package org.example.project.presentations.screen.message

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.MessagePage
import org.example.project.domain.model.MessageStatus
import org.example.project.domain.model.MessageType
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

    private val chatUserId: String = savedStateHandle["studentId"] ?: ""
    private val chatUserName: String = savedStateHandle["chatName"] ?: ""

    private val currentUserId: String =
        studentUseCase.studentInfo.value?.studentCode?.lowercase() ?: ""

    val roomId: String = generateRoomId(currentUserId, chatUserId.lowercase())

    private val _uiState = MutableStateFlow(
        MessageState(
            chatUser = User(
                id = chatUserId,
                name = chatUserName,
                avatarUrl = "",
                isOnline = false
            )
        )
    )
    val uiState = _uiState.asStateFlow()

    private var lastDocument: DocumentSnapshot? = null

    private val remoteMessages = messageRepository
        .observeMessages(roomId, currentUserId)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000), replay = 1)

    val messages: StateFlow<List<MessageUiState>> = combine(
        remoteMessages,
        uiState.map { it.pendingMessages }.distinctUntilChanged(),
        uiState.map { it.olderMessages }.distinctUntilChanged()
    ) { remote: List<MessageUiState>, pending: List<MessageUiState>, older: List<MessageUiState> ->
        val latestRemoteTime = remote.maxOfOrNull { it.timestamp } ?: 0L
        val stillPending = pending.filter { it.timestamp > latestRemoteTime }
        (older + remote + stillPending)
            .distinctBy { it.id }
            .sortedBy { it.timestamp }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        observeMessagesLoaded()
        markAsRead()
        observeUserOnlineStatus()
        observeAndMarkNewMessages()
        loadChatStudent()
        loadInitialMessages()
    }

    private fun loadInitialMessages() {
        viewModelScope.launch {
            val page: MessagePage = messageRepository.loadOlderMessages(
                roomId = roomId,
                currentUserId = currentUserId,
                lastDocument = null
            )
            lastDocument = page.lastDocument
            updateState {
                copy(
                    olderMessages = page.messages,
                    hasMoreMessages = page.hasMore
                )
            }
        }
    }

    private fun loadChatStudent() {
        viewModelScope.launch {
            studentUseCase.getStudentInfo(chatUserId)
                .onSuccess { student -> updateState { copy(chatStudent = student) } }
                .onFailure { Log.e("MessageViewModel", "loadChatStudent: $it") }
        }
    }

    fun loadMoreMessages() {
        val state = _uiState.value
        if (state.isLoadingMore || !state.hasMoreMessages) return

        viewModelScope.launch {
            updateState { copy(isLoadingMore = true) }
            delay(1000)
            try {
                val page: MessagePage = messageRepository.loadOlderMessages(
                    roomId = roomId,
                    currentUserId = currentUserId,
                    lastDocument = lastDocument
                )
                lastDocument = page.lastDocument
                updateState {
                    copy(
                        olderMessages = page.messages + olderMessages,
                        hasMoreMessages = page.hasMore
                    )
                }
            } finally {
                updateState { copy(isLoadingMore = false) }
            }
        }
    }

    private fun observeMessagesLoaded() {
        viewModelScope.launch {
            remoteMessages.first()
            updateState { copy(isLoading = false) }
        }
    }

    private fun observeUserOnlineStatus() {
        viewModelScope.launch {
            messageRepository.observeUserOnlineStatus(chatUserId)
                .collect { isOnline ->
                    updateState { copy(chatUser = chatUser?.copy(isOnline = isOnline)) }
                }
        }
    }

    private fun observeAndMarkNewMessages() {
        viewModelScope.launch {
            remoteMessages
                .distinctUntilChangedBy { it.maxOfOrNull { m -> m.timestamp } ?: 0L }
                .drop(1)
                .filter { it.lastOrNull()?.isMe == false }
                .collect { messageRepository.markConversationAsRead(roomId, currentUserId) }
        }
    }

    private fun markAsRead() {
        viewModelScope.launch {
            messageRepository.markConversationAsRead(roomId, currentUserId)
        }
    }

    fun onMessageChange(message: String) {
        updateState { copy(message = message) }
    }

    fun onImageSelected(uri: Uri?, context: Context) {
        updateState { copy(selectedImageUri = uri, selectedImageBytes = null) }
        if (uri == null) return
        viewModelScope.launch(Dispatchers.IO) {
            val bytes = context.contentResolver.openInputStream(uri)?.readBytes()
            updateState { copy(selectedImageBytes = bytes) }
        }
    }

    fun onRemoveImage() {
        updateState { copy(selectedImageUri = null, selectedImageBytes = null) }
    }

    fun onFileSelected(uri: Uri?, context: Context) {
        updateState { copy(selectedFileUri = uri, selectedFileBytes = null) }
        if (uri == null) return
        viewModelScope.launch(Dispatchers.IO) {
            val bytes = context.contentResolver.openInputStream(uri)?.readBytes()
            updateState { copy(selectedFileBytes = bytes) }
        }
    }

    fun onRemoveFile() {
        updateState { copy(selectedFileUri = null, selectedFileBytes = null) }
    }

    fun onSend(fileName: String? = null, fileSize: String? = null) {
        val state = _uiState.value
        val text = state.message.trim()
        val imageBytes = state.selectedImageBytes
        val fileBytes = state.selectedFileBytes

        if (text.isBlank() && imageBytes == null && fileBytes == null) return

        updateState {
            copy(
                message = "",
                selectedImageUri = null, selectedImageBytes = null,
                selectedFileUri = null, selectedFileBytes = null
            )
        }

        when {
            imageBytes != null -> sendImageMessage(
                imageUri = state.selectedImageUri,
                imageBytes = imageBytes,
                caption = text.ifBlank { null }
            )

            fileBytes != null -> sendFileMessage(
                fileUri = state.selectedFileUri,
                fileBytes = fileBytes,
                caption = text.ifBlank { null },
                fileName = fileName,
                fileSize = fileSize
            )

            else -> sendTextMessage(text)
        }
    }

    private fun sendTextMessage(text: String) {
        val pending = buildPendingMessage(type = MessageType.TEXT, text = text)
        addPending(pending)
        viewModelScope.launch {
            messageRepository.sendMessage(roomId, currentUserId, text)
        }
    }

    private fun sendImageMessage(imageUri: Uri?, imageBytes: ByteArray, caption: String?) {
        val pending = buildPendingMessage(
            type = MessageType.IMAGE,
            text = caption,
            fileUrl = imageUri?.toString()
        )
        addPending(pending)
        viewModelScope.launch {
            messageRepository.sendImageMessage(
                roomId = roomId,
                senderId = currentUserId,
                imageBytes = imageBytes,
                caption = caption
            )
        }
    }

    private fun sendFileMessage(
        fileUri: Uri?,
        fileBytes: ByteArray,
        caption: String?,
        fileName: String?,
        fileSize: String?
    ) {
        val pending = buildPendingMessage(
            type = MessageType.FILE,
            text = caption,
            fileUrl = fileUri?.toString(),
            fileName = fileName,
            fileSize = fileSize
        )
        addPending(pending)
        viewModelScope.launch {
            messageRepository.sendFileMessage(
                roomId = roomId,
                senderId = currentUserId,
                fileBytes = fileBytes,
                caption = caption,
                fileName = fileName ?: fileUri?.lastPathSegment ?: "File",
                fileSize = fileSize ?: "36kb"
            )
        }
    }

    private fun buildPendingMessage(
        type: MessageType,
        text: String? = null,
        fileUrl: String? = null,
        fileName: String? = null,
        fileSize: String? = null
    ) = MessageUiState(
        id = UUID.randomUUID().toString(),
        senderId = currentUserId,
        text = text,
        fileUrl = fileUrl,
        type = type.name,
        timestamp = System.currentTimeMillis(),
        isMe = true,
        status = MessageStatus.SENDING,
        fileName = fileName,
        fileSize = fileSize
    )

    private fun addPending(message: MessageUiState) {
        updateState { copy(pendingMessages = pendingMessages + message) }
    }

    private fun updateState(block: MessageState.() -> MessageState) {
        _uiState.update { it.block() }
    }

    private fun generateRoomId(user1: String, user2: String): String =
        listOf(user1, user2).sorted().joinToString("_")
}