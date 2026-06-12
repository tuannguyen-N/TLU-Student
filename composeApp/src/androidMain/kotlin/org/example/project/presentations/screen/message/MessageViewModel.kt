package org.example.project.presentations.screen.message

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.remote.dto.chatbot.ChatMessageContext
import org.example.project.domain.MessagePage
import org.example.project.domain.model.MessageStatus
import org.example.project.domain.model.MessageType
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.model.SenderType
import org.example.project.domain.model.SseEvent
import org.example.project.domain.model.UserUiModel
import org.example.project.domain.repository.ChatRepository
import org.example.project.domain.repository.MessageRepository
import org.example.project.domain.repository.PresenceRepository
import org.example.project.domain.repository.SummaryRepository
import org.example.project.domain.usecase.StudentUseCase
import org.example.project.domain.usecase.SummaryUseCase
import org.example.project.presentations.utils.ChatPresenceManager
import java.util.UUID

class MessageViewModel(
    savedStateHandle: SavedStateHandle,
    private val messageRepository: MessageRepository,
    private val studentUseCase: StudentUseCase,
    private val presenceRepository: PresenceRepository,
    private val chatRepository: ChatRepository,
    private val summaryUseCase: SummaryUseCase
) : ViewModel() {

    private val chatUserId: String = savedStateHandle["studentId"] ?: ""
    private val chatUserName: String = savedStateHandle["chatName"] ?: ""
    private val chatUserAvatarUrl: String = savedStateHandle["avatarUrl"] ?: ""

    private val currentUserId: String =
        studentUseCase.studentInfo.value?.studentCode?.lowercase() ?: ""

    val roomId: String = generateRoomId(currentUserId, chatUserId.lowercase())

    private val _uiState = MutableStateFlow(
        MessageState(
            chatUser = UserUiModel(
                studentCode = chatUserId,
                name = chatUserName,
                avatarUrl = chatUserAvatarUrl
            )
        )
    )

    val uiState = _uiState.asStateFlow()

    private var lastDocument: DocumentSnapshot? = null

    private val remoteMessages = messageRepository.observeMessages(roomId, currentUserId)
        .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    val messages: StateFlow<List<MessageUiState>> = combine(
        remoteMessages,
        uiState.map { it.pendingMessages }.distinctUntilChanged(),
        uiState.map { it.olderMessages }.distinctUntilChanged()
    ) { remote, pending, older ->
        val latestRemoteTime = remote.maxOfOrNull { it.timestamp } ?: 0L
        val remoteIds = remote.map { it.id }.toSet()
        val stillPending = pending.filter { p ->
            when {
                p.senderType == SenderType.AI && p.status == MessageStatus.SENDING -> true
                p.senderType == SenderType.AI && p.status == MessageStatus.SENT ->
                    remote.none { r -> r.senderType == SenderType.AI && r.text == p.text }
                else -> p.timestamp > latestRemoteTime
            }
        }
        val filteredOlder = older.filter { it.id !in remoteIds }
        (filteredOlder + remote + stillPending)
            .sortedBy { messageSortKey(it, latestRemoteTime, remoteIds) }
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        observeMessagesLoaded()
        markAsRead()
        observeUserOnlineStatus()
        observeAndMarkNewMessages()
        loadChatStudent()
        loadInitialMessages()
        enterChatRoom()
        loadChatContext()
    }

    private fun loadChatContext() {
        viewModelScope.launch {
            chatRepository.refreshChatbotContext()
        }
    }

    private fun loadInitialMessages() {
        viewModelScope.launch {
            val page: MessagePage = messageRepository.loadOlderMessages(
                roomId = roomId, currentUserId = currentUserId, lastDocument = null
            )
            lastDocument = page.lastDocument
            updateState {
                copy(
                    olderMessages = page.messages, hasMoreMessages = page.hasMore
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
                    roomId = roomId, currentUserId = currentUserId, lastDocument = lastDocument
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

    fun summarize(messageId: String) {
        val aiMessageId = UUID.randomUUID().toString()
        val aiTimestamp = maxOf(
            messages.value.maxOfOrNull { it.timestamp } ?: 0L,
            _uiState.value.pendingMessages.maxOfOrNull { it.timestamp } ?: 0L,
            System.currentTimeMillis()
        ) + 1

        updateState {
            copy(
                isAiReplying = true,
                pendingMessages = pendingMessages + MessageUiState(
                    id = aiMessageId,
                    senderId = "tlu_ai",
                    text = "",
                    timestamp = aiTimestamp,
                    isMe = false,
                    status = MessageStatus.SENDING,
                    type = MessageType.TEXT.name,
                    senderType = SenderType.AI
                )
            )
        }

        summaryUseCase.summarize(roomId, messageId)
            .onEach { event ->
                when (event) {
                    is SseEvent.Token -> {
                        val decoded = event.text
                            .replace("\\n", "\n")
                            .replace("\\r", "")
                            .replace("\\t", "\t")

                        updateState {
                            copy(
                                pendingMessages = pendingMessages.map {
                                    if (it.id == aiMessageId) {
                                        it.copy(text = (it.text ?: "") + decoded)
                                    } else it
                                }
                            )
                        }
                    }

                    is SseEvent.Done -> {
                        updateState {
                            copy(
                                isAiReplying = false,
                                pendingMessages = pendingMessages.map {
                                    if (it.id == aiMessageId) {
                                        it.copy(status = MessageStatus.SENT)
                                    } else it
                                }
                            )
                        }
                    }

                    is SseEvent.Error -> {
                        updateState {
                            copy(
                                isAiReplying = false,
                                pendingMessages = pendingMessages.map {
                                    if (it.id == aiMessageId) {
                                        it.copy(
                                            text = event.message,
                                            status = MessageStatus.FAILED
                                        )
                                    } else it
                                }
                            )
                        }
                    }
                }
            }
            .catch {
                updateState {
                    copy(
                        isAiReplying = false,
                        pendingMessages = pendingMessages.map {
                            if (it.id == aiMessageId) {
                                it.copy(
                                    text = "Server đang bận",
                                    status = MessageStatus.FAILED
                                )
                            } else it
                        }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeMessagesLoaded() {
        viewModelScope.launch {
            remoteMessages.first()
            updateState { copy(isLoading = false) }
        }
    }

    private fun observeUserOnlineStatus() {
        viewModelScope.launch {
            presenceRepository.observePresence(chatUserId).collect { chatUserPresence ->
                updateState {
                    copy(
                        chatUser = chatUser?.copy(
                            isOnline = chatUserPresence.isOnline,
                            lastSeen = chatUserPresence.lastSeen
                        )
                    )
                }
            }
        }
    }

    private fun observeAndMarkNewMessages() {
        viewModelScope.launch {
            remoteMessages.distinctUntilChangedBy { it.maxOfOrNull { m -> m.timestamp } ?: 0L }
                .drop(1).filter { it.lastOrNull()?.isMe == false }
                .collect { messageRepository.markConversationAsRead(roomId, currentUserId) }
        }
    }

    private fun markAsRead() {
        viewModelScope.launch {
            messageRepository.markConversationAsRead(roomId, currentUserId)
        }
    }

    fun onMessageChange(value: TextFieldValue) {
        updateState {
            copy(message = value)
        }
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
        val text = state.message.text.trim()
        val imageBytes = state.selectedImageBytes
        val fileBytes = state.selectedFileBytes

        if (text.isBlank() && imageBytes == null && fileBytes == null) return

        updateState {
            copy(
                message = TextFieldValue(text = "", selection = TextRange(0)),
                selectedImageUri = null,
                selectedImageBytes = null,
                selectedFileUri = null,
                selectedFileBytes = null
            )
        }

        when {
            imageBytes != null -> sendImageMessage(
                imageUri = state.selectedImageUri,
                imageBytes = imageBytes,
                caption = text.ifBlank { null })

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
            messageRepository.sendMessage(roomId, currentUserId, SenderType.USER, text)
        }

        if (isMentionTluAi(text)) {
            askTluAi(text, afterTimestamp = pending.timestamp)
        }
    }

    private fun askTluAi(message: String, afterTimestamp: Long = 0L) {
        val prompt = message
            .replace("@tlu_ai", "")
            .trim()

        val aiMessageId = UUID.randomUUID().toString()
        val aiTimestamp = maxOf(
            messages.value.maxOfOrNull { it.timestamp } ?: 0L,
            _uiState.value.pendingMessages.maxOfOrNull { it.timestamp } ?: 0L,
            afterTimestamp,
            System.currentTimeMillis()
        ) + 1

        updateState {
            copy(
                isAiReplying = true,
                pendingMessages = pendingMessages + MessageUiState(
                    id = aiMessageId,
                    senderId = "tlu_ai",
                    text = "",
                    timestamp = aiTimestamp,
                    isMe = false,
                    status = MessageStatus.SENDING,
                    type = MessageType.TEXT.name,
                    senderType = SenderType.AI
                )
            )
        }

        val contextMessages = messages.value
            .filter {
                it.senderType == SenderType.AI ||
                        (it.senderType == SenderType.USER && isMentionTluAi(it.text ?: ""))
            }
            .takeLast(3)
            .map {
                ChatMessageContext(
                    content = it.text?.replace(Regex("(?<!\\w)@tlu_ai(?!\\w)"), "")?.trim()
                        ?: "",
                    role = if (it.senderType == SenderType.AI) "assistant" else "user"
                )
            }

        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.streamChat(
                prompt = prompt,
                messages = contextMessages,
            )
                .onStart { updateState { copy(isAiReplying = true) } }
                .onEach { event ->
                    when (event) {
                        is SseEvent.Token -> {
                            val decodedText = event.text
                                .replace("\\n", "\n")
                                .replace("\\r", "")
                                .replace("\\t", "\t")
                            Log.e("STREAM", "token=${decodedText}")

                            updateState {
                                val existing = pendingMessages.find { it.id == aiMessageId }
                                if (existing == null) {
                                    copy(
                                        pendingMessages = pendingMessages +
                                                MessageUiState(
                                                    id = aiMessageId,
                                                    senderId = "tlu_ai",
                                                    text = decodedText,
                                                    timestamp = aiTimestamp,
                                                    isMe = false,
                                                    status = MessageStatus.SENDING,
                                                    type = MessageType.TEXT.name,
                                                    senderType = SenderType.AI
                                                )
                                    )
                                } else {
                                    copy(
                                        pendingMessages =
                                            pendingMessages.map {
                                                if (it.id == aiMessageId) {
                                                    it.copy(text = (it.text ?: "") + decodedText)
                                                } else {
                                                    it
                                                }
                                            }
                                    )
                                }
                            }
                        }

                        is SseEvent.Done -> {
                            val aiMessage =
                                _uiState.value.pendingMessages.find { it.id == aiMessageId }

                            updateState {
                                copy(
                                    isAiReplying = false,
                                    pendingMessages =
                                        pendingMessages.map {
                                            if (it.id == aiMessageId) {
                                                it.copy(status = MessageStatus.SENT)
                                            } else {
                                                it
                                            }
                                        }
                                )
                            }

                            aiMessage?.text?.let { content ->
                                messageRepository.sendMessage(
                                    roomId = roomId,
                                    currentUserId = "tlu_ai",
                                    senderType = SenderType.AI,
                                    message = content
                                )
                            }
                        }

                        is SseEvent.Error -> {
                            updateState {
                                copy(
                                    isAiReplying = false,
                                    pendingMessages =
                                        pendingMessages.map {
                                            if (it.id == aiMessageId) {
                                                it.copy(status = MessageStatus.FAILED)
                                            } else {
                                                it
                                            }
                                        }
                                )
                            }
                        }
                    }
                }
                .catch {
                    updateState {
                        copy(
                            isAiReplying = false,
                            pendingMessages =
                                pendingMessages.map {
                                    if (it.id == aiMessageId) {
                                        it.copy(status = MessageStatus.FAILED)
                                    } else {
                                        it
                                    }
                                }
                        )
                    }
                }
                .launchIn(this)
        }
    }

    private fun isMentionTluAi(text: String): Boolean {
        return Regex("(?<!\\w)@tlu_ai(?!\\w)")
            .containsMatchIn(text)
    }

    private fun sendImageMessage(imageUri: Uri?, imageBytes: ByteArray, caption: String?) {
        val pending = buildPendingMessage(
            type = MessageType.IMAGE, text = caption, fileUrl = imageUri?.toString()
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
        fileUri: Uri?, fileBytes: ByteArray, caption: String?, fileName: String?, fileSize: String?
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

    fun enterChatRoom() {
        ChatPresenceManager.currentRoom.value = roomId
    }

    fun leaveChatRoom() {
        ChatPresenceManager.currentRoom.value = null
    }

    private fun addPending(message: MessageUiState) {
        updateState { copy(pendingMessages = pendingMessages + message) }
    }

    private fun updateState(block: MessageState.() -> MessageState) {
        _uiState.update { it.block() }
    }

    private fun generateRoomId(user1: String, user2: String): String =
        listOf(user1, user2).sorted().joinToString("_")

    private fun messageSortKey(
        msg: MessageUiState,
        latestRemoteTime: Long,
        remoteIds: Set<String>
    ): Long {
        if (msg.senderType == SenderType.AI && msg.id !in remoteIds) {
            return maxOf(msg.timestamp, latestRemoteTime + 1)
        }
        return msg.timestamp
    }

    override fun onCleared() {
        leaveChatRoom()
        super.onCleared()
    }
}