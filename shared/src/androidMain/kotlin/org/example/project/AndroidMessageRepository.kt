package org.example.project

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import org.example.project.data.mapper.toConversationUiState
import org.example.project.data.mapper.toUiState
import org.example.project.data.remote.api.FileUploadApi
import org.example.project.domain.model.ChatRoom
import org.example.project.domain.model.ConversationUiState
import org.example.project.domain.model.Message
import org.example.project.domain.model.MessageStatus
import org.example.project.domain.model.MessageType
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.model.User
import org.example.project.domain.repository.MessageRepository

class AndroidMessageRepository(
    private val fileUploadApi: FileUploadApi
) : MessageRepository {
    private val firestore = FirebaseFirestore.getInstance()

    private val usersState = MutableStateFlow<Map<String, User>>(emptyMap())
    private val userListeners =
        mutableMapOf<String, com.google.firebase.firestore.ListenerRegistration>()

    override fun observeConversations(
        currentStudentId: String
    ): Flow<List<ConversationUiState>> {

        val roomsFlow = callbackFlow {
            val listener = firestore
                .collection("chatRooms")
                .whereArrayContains(
                    "participantIds",
                    currentStudentId
                )
                .orderBy(
                    "lastMessageTime",
                    Query.Direction.DESCENDING
                )
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val rooms = snapshot?.documents
                        ?.mapNotNull {
                            it.toObject(ChatRoom::class.java)
                        }
                        ?: emptyList()

                    trySend(rooms)
                }

            awaitClose { listener.remove() }
        }

        return combine(
            roomsFlow,
            usersState
        ) { rooms, users ->
            rooms.map { room ->
                val otherUserId =
                    room.participantIds.first {
                        it != currentStudentId
                    }
                setupUserListener(otherUserId)
                room.toConversationUiState(
                    currentStudentId,
                    users[otherUserId]
                )
            }
        }
    }

    override fun observeMessages(
        roomId: String,
        currentUserId: String,
        limit: Long
    ): Flow<List<MessageUiState>> {
        val messagesFlow = callbackFlow {
            val listener = firestore
                .collection("chatRooms")
                .document(roomId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(limit)
                .addSnapshotListener { snapshot, error ->

                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val messages =
                        snapshot?.documents?.mapNotNull { doc ->
                            doc.toObject(Message::class.java)
                        } ?: emptyList()

                    trySend(messages.reversed())
                }

            awaitClose { listener.remove() }
        }

        val lastReadAtFlow = callbackFlow {
            val listener = firestore
                .collection("chatRooms")
                .document(roomId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error); return@addSnapshotListener
                    }
                    @Suppress("UNCHECKED_CAST")
                    val lastReadAt = snapshot?.get("lastReadAt") as? Map<String, Long> ?: emptyMap()
                    trySend(lastReadAt)
                }
            awaitClose { listener.remove() }
        }

        return combine(messagesFlow, lastReadAtFlow) { messages, lastReadAt ->
            val otherUserId = lastReadAt.keys.firstOrNull {
                !it.equals(currentUserId, ignoreCase = true)
            }
            val otherLastRead = otherUserId?.let { lastReadAt[it] } ?: 0L

            val lastMyMessageIndex = messages.indexOfLast {
                it.senderId.equals(currentUserId, ignoreCase = true)
            }

            messages.mapIndexed { index, message ->
                val isMe = message.senderId.equals(currentUserId, ignoreCase = true)

                val status = when {
                    !isMe -> MessageStatus.SEEN
                    index == lastMyMessageIndex -> {
                        if (otherLastRead >= message.timestamp) MessageStatus.SEEN
                        else MessageStatus.SENT
                    }

                    else -> MessageStatus.SENT
                }

                message.toUiState(currentUserId, status)
            }
        }
    }

    override suspend fun markConversationAsRead(
        roomId: String,
        currentUserId: String
    ) {
        val roomRef = firestore
            .collection("chatRooms")
            .document(roomId)

        val snapshot = roomRef.get().await()
        if (!snapshot.exists()) return

        roomRef.update(
            mapOf(
                "unreadCounts.$currentUserId" to 0,
                "lastReadAt.$currentUserId" to System.currentTimeMillis()
            )
        ).await()
    }

    override suspend fun sendMessage(
        roomId: String,
        currentUserId: String,
        message: String
    ) {
        val participantIds = roomId.split("_")
        val receiverId = participantIds.first { it != currentUserId }

        createRoomIfNeeded(
            roomId = roomId,
            currentUserId = currentUserId,
            receiverId = receiverId,
            firstMessage = message
        )

        val roomRef = firestore.collection("chatRooms").document(roomId)
        val messageRef = roomRef.collection("messages").document()

        val currentTimeMillis = System.currentTimeMillis()

        val messageData = hashMapOf(
            "id" to messageRef.id,
            "senderId" to currentUserId,
            "text" to message,
            "type" to MessageType.TEXT.name,
            "timestamp" to currentTimeMillis
        )

        firestore.runTransaction { transaction ->

            transaction.set(messageRef, messageData)

            transaction.update(
                roomRef,
                mapOf(
                    "lastMessageText" to message,
                    "lastMessageTime" to currentTimeMillis,
                    "lastSenderId" to currentUserId,
                    "unreadCounts.$receiverId" to FieldValue.increment(1)
                )
            )
        }.await()
    }

    override suspend fun sendImageMessage(
        roomId: String,
        senderId: String,
        imageBytes: ByteArray,
        caption: String?
    ) {
        val participantIds = roomId.split("_")
        val receiverId = participantIds.first { it != senderId }
        createRoomIfNeeded(
            roomId = roomId,
            currentUserId = senderId,
            receiverId = receiverId,
            firstMessage = "📷 Hình ảnh"
        )

        val currentTimeMillis = System.currentTimeMillis()

        val imageUrl = fileUploadApi.uploadFile(
            fileName = "${roomId}_$currentTimeMillis.jpg",
            fileBytes = imageBytes
        )

        val roomRef = firestore.collection("chatRooms").document(roomId)
        val messageRef = roomRef.collection("messages").document()

        val messageData = hashMapOf(
            "id" to messageRef.id,
            "senderId" to senderId,
            "text" to (caption ?: ""),
            "fileUrl" to imageUrl.data.url,
            "fileName" to null,               // Image không có fileName
            "type" to MessageType.IMAGE.name,
            "timestamp" to currentTimeMillis
        )

        firestore.runTransaction { transaction ->
            transaction.set(messageRef, messageData)
            transaction.update(
                roomRef,
                mapOf(
                    "lastMessageText" to "📷 Hình ảnh",
                    "lastMessageTime" to currentTimeMillis,
                    "lastSenderId" to senderId,
                    "unreadCounts.$receiverId" to FieldValue.increment(1)
                )
            )
        }.await()
    }

    override suspend fun sendFileMessage(
        roomId: String,
        senderId: String,
        fileBytes: ByteArray,
        fileName: String,
        fileSize: String,
        caption: String?
    ) {
        val participantIds = roomId.split("_")
        val receiverId = participantIds.first { it != senderId }
        createRoomIfNeeded(
            roomId = roomId,
            currentUserId = senderId,
            receiverId = receiverId,
            firstMessage = "📎 Tệp đính kèm"
        )

        val currentTimeMillis = System.currentTimeMillis()

        val fileUrl = fileUploadApi.uploadFile(
            fileName = "file_${roomId}_$currentTimeMillis",
            fileBytes = fileBytes
        )

        val roomRef = firestore.collection("chatRooms").document(roomId)
        val messageRef = roomRef.collection("messages").document()

        val messageData = hashMapOf(
            "id" to messageRef.id,
            "senderId" to senderId,
            "text" to (caption ?: ""),
            "fileUrl" to fileUrl.data.url,
            "fileName" to fileName,
            "fileSize" to fileSize,
            "type" to MessageType.FILE.name,
            "timestamp" to currentTimeMillis
        )

        firestore.runTransaction { transaction ->
            transaction.set(messageRef, messageData)
            transaction.update(
                roomRef,
                mapOf(
                    "lastMessageText" to "📎 $fileName",
                    "lastMessageTime" to currentTimeMillis,
                    "lastSenderId" to senderId,
                    "unreadCounts.$receiverId" to FieldValue.increment(1)
                )
            )
        }.await()
    }

    private suspend fun createRoomIfNeeded(
        roomId: String,
        currentUserId: String,
        receiverId: String,
        firstMessage: String
    ) {
        val roomRef = firestore
            .collection("chatRooms")
            .document(roomId)

        val snapshot = roomRef.get().await()

        if (snapshot.exists()) return

        val currentTimeMillis = System.currentTimeMillis()

        val room = ChatRoom(
            id = roomId,
            participantIds = listOf(currentUserId, receiverId),
            lastMessageText = firstMessage,
            lastMessageTime = currentTimeMillis,
            lastSenderId = currentUserId,
            unreadCounts = mapOf(
                currentUserId to 0,
                receiverId to 1
            ),
            lastReadAt = mapOf(
                currentUserId to currentTimeMillis,
                receiverId to 0L
            )
        )

        roomRef.set(room).await()
    }

    override fun observeUserOnlineStatus(
        userId: String
    ): Flow<Boolean> = callbackFlow {

        val listener = firestore
            .collection("users")
            .document(userId)
            .addSnapshotListener { snapshot, error ->

                val online = snapshot?.getBoolean("isOnline") ?: false

                Log.d(
                    "ONLINE_FIRESTORE",
                    "user=$userId online=$online"
                )

                trySend(online)
            }

        awaitClose { listener.remove() }
    }

    private fun setupUserListener(userId: String) {
        if (userListeners.containsKey(userId)) {
            return
        }

        val listener = firestore
            .collection("users")
            .document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("USER_LISTENER_ERROR", "Error listening to user: $userId", error)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val user = User(
                        id = snapshot.getString("id") ?: "",
                        name = snapshot.getString("name") ?: "",
                        avatarUrl = snapshot.getString("avatarUrl"),
                        isOnline = snapshot.getBoolean("isOnline") ?: false
                    )
                    usersState.update { it + (userId to user) }
                    Log.d("USER_UPDATE", "User $userId updated: isOnline=${user.isOnline}")
                }
            }

        userListeners[userId] = listener
    }

    private fun removeUserListener(userId: String) {
        userListeners[userId]?.remove()
        userListeners.remove(userId)
    }
}