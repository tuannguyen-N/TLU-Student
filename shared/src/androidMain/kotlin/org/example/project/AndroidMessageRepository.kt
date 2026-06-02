package org.example.project

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.example.project.data.mapper.toConversationUiState
import org.example.project.domain.model.ChatRoom
import org.example.project.domain.model.ConversationUiState
import org.example.project.domain.model.User
import org.example.project.domain.repository.MessageRepository

class AndroidMessageRepository : MessageRepository {
    private val firestore = FirebaseFirestore.getInstance()

    override fun observeConversations(
        currentUserId: String
    ): Flow<List<ConversationUiState>> = callbackFlow {

        val listener = firestore
            .collection("chatRooms")
            .whereArrayContains(
                "participantIds",
                currentUserId
            )
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                CoroutineScope(Dispatchers.IO).launch {
                    val rooms = snapshot?.documents
                        ?.mapNotNull {
                            it.toObject(ChatRoom::class.java)
                        } ?: emptyList()
                    val conversations = rooms.map { room ->
                        val otherUserId =
                            room.participantIds.firstOrNull {
                                it != currentUserId
                            } ?: ""

                        val otherUser = getUser(otherUserId)

                        room.toConversationUiState(
                            currentUserId,
                            otherUser
                        )
                    }
                    trySend(conversations)
                }
            }

        awaitClose {
            listener.remove()
        }
    }

    private suspend fun getUser(
        userId: String
    ): User? {
        return firestore
            .collection("users")
            .document(userId)
            .get()
            .await()
            .toObject(User::class.java)
    }
}