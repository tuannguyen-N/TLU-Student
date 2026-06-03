package org.example.project

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.example.project.domain.model.User
import org.example.project.domain.repository.UserRepository

class AndroidUserRepository : UserRepository {
    val firestore = FirebaseFirestore.getInstance()

    override suspend fun updateOnlineStatus(
        isOnline: Boolean,
        currentUserId: String
    ) {
        firestore
            .collection("users")
            .document(currentUserId)
            .update(
                mapOf(
                    "isOnline" to isOnline,
                    "lastSeen" to System.currentTimeMillis()
                )
            )
            .await()
    }

    override suspend fun getUsers(
        size: Int,
        excludeUserId: String?
    ): List<User> {
        val snapshot = firestore
            .collection("users")
            .limit(size.toLong())
            .get()
            .await()

        return snapshot.documents
            .map {
                User(
                    id = it.getString("id") ?: "",
                    name = it.getString("name") ?: "",
                    avatarUrl = it.getString("avatarUrl") ?: "",
                    isOnline = it.getBoolean("isOnline") ?: false
                )
            }
            .filter {
                it.id != excludeUserId
            }
    }
}