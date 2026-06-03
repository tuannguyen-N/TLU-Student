package org.example.project

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
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
    private val userCache = mutableMapOf<String, User>()

    override fun observeConversations(
        currentStudentId: String
    ): Flow<List<ConversationUiState>> = channelFlow {
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
                launch {
                    val rooms = snapshot?.documents
                        ?.mapNotNull {
                            it.toObject(ChatRoom::class.java)
                        } ?: emptyList()
                    val conversations = coroutineScope {
                        rooms.map { room ->
                            async {
                                val otherUserId =
                                    room.participantIds.firstOrNull {
                                        it != currentStudentId
                                    } ?: ""

                                val otherUser = getUser(otherUserId)

                                room.toConversationUiState(
                                    currentStudentId,
                                    otherUser
                                )
                            }
                        }.awaitAll()
                    }
                    trySend(conversations)
                }
            }
        awaitClose { listener.remove() }
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
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val isOnline = snapshot?.getBoolean("isOnline") ?: false
                trySend(isOnline)
            }

        awaitClose {
            listener.remove()
        }
    }

    private suspend fun getUser(userId: String): User? {
        userCache[userId]?.let {
            return it
        }
        val user = firestore
            .collection("users")
            .document(userId)
            .get()
            .await()
            .toObject(User::class.java)

        if (user != null) {
            userCache[userId] = user
        }
        return user
    }
}