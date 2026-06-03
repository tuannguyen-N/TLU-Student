package org.example.project

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import org.example.project.data.mapper.toConversationUiState
import org.example.project.data.mapper.toUiState
import org.example.project.domain.model.ChatRoom
import org.example.project.domain.model.ConversationUiState
import org.example.project.domain.model.Message
import org.example.project.domain.model.MessageType
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.model.User
import org.example.project.domain.repository.MessageRepository

class AndroidMessageRepository : MessageRepository {
    private val firestore = FirebaseFirestore.getInstance()

    //    private val userCache = mutableMapOf<String, User>()
    private val usersState = MutableStateFlow<Map<String, User>>(emptyMap())
    private val userListeners =
        mutableMapOf<String, com.google.firebase.firestore.ListenerRegistration>()

    override fun observeConversations(
        currentStudentId: String
    ): Flow<List<ConversationUiState>> {

        val roomsFlow = callbackFlow<List<ChatRoom>> {
            val listener = firestore
                .collection("chatRooms")
                .whereArrayContains(
                    "participantIds",
                    currentStudentId
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
        currentUserId: String
    ): Flow<List<MessageUiState>> = callbackFlow {
        val listener = firestore
            .collection("chatRooms")
            .document(roomId)
            .collection("messages")
            .orderBy("timestamp")
            .limitToLast(30)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val messages = snapshot?.documents?.mapNotNull { doc ->
                    try {
                        doc.toObject(Message::class.java)
                            ?.toUiState(currentUserId)

                    } catch (e: Exception) {
                        Log.e(
                            "MESSAGE_ERROR",
                            "docId=${doc.id} data=${doc.data}",
                            e
                        )
                        null
                    }
                } ?: emptyList()
                trySend(messages)
            }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun markConversationAsRead(
        roomId: String,
        currentUserId: String
    ) {
        firestore
            .collection("chatRooms")
            .document(roomId)
            .update(
                mapOf(
                    "unreadCounts.$currentUserId" to 0,
                    "lastReadAt.$currentUserId" to System.currentTimeMillis()
                )
            )
            .await()
    }

    override suspend fun sendMessage(
        roomId: String,
        currentUserId: String,
        message: String
    ) {
        val roomRef = firestore.collection("chatRooms").document(roomId)
        val messageRef = roomRef.collection("messages").document()

        val currentTimeMillis = System.currentTimeMillis()

        val messageObj = Message(
            id = messageRef.id,
            senderId = currentUserId,
            text = message,
            type = MessageType.TEXT.name,
            timestamp = currentTimeMillis
        )

        firestore.runTransaction { transaction ->
            val roomSnapshot = transaction.get(roomRef)
            val participantIds =
                roomSnapshot.get("participantIds") as? List<*> ?: return@runTransaction

            val receiverId = participantIds.firstOrNull { it != currentUserId } as? String
                ?: return@runTransaction

            val messageData = hashMapOf(
                "id" to messageObj.id,
                "senderId" to messageObj.senderId,
                "text" to messageObj.text,
                "type" to messageObj.type,
                "timestamp" to currentTimeMillis
            )

            val roomUpdateData = hashMapOf(
                "lastMessageText" to message,
                "lastMessageTime" to currentTimeMillis,
                "lastSenderId" to currentUserId,
                "unreadCounts.$receiverId" to FieldValue.increment(1)
            )

            transaction.set(messageRef, messageData)
            transaction.update(roomRef, roomUpdateData)
        }.await()
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

    private suspend fun getUser(userId: String): User? {
        val cachedUser = usersState.value[userId]
        if (cachedUser != null) return cachedUser

        val snapshot = firestore
            .collection("users")
            .document(userId)
            .get()
            .await()

        Log.d("GET_USER", "raw data: ${snapshot.data}")
        Log.d("GET_USER", "isOnline raw: ${snapshot.getBoolean("isOnline")}")

        val user = User(
            id = snapshot.getString("id") ?: "",
            name = snapshot.getString("name") ?: "",
            avatarUrl = snapshot.getString("avatarUrl"),
            isOnline = snapshot.getBoolean("isOnline") ?: false
        )

        Log.d("GET_USER", "user after deserialize: $user")

        usersState.update { it + (userId to user) }
        return user
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