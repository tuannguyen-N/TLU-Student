package org.example.project

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import org.example.project.data.local.FirebaseStorage
import org.example.project.data.remote.dto.student_search.StudentSummary
import org.example.project.domain.model.User
import org.example.project.domain.repository.UserRepository

class AndroidUserRepository(
    private val firebaseStorage: FirebaseStorage
) : UserRepository {
    val firestore = FirebaseFirestore.getInstance()

    override suspend fun getUsers(
        size: Int,
        excludeUserId: String?
    ): List<User> {
        val snapshot = firestore
            .collection("users")
            .orderBy("lastSeen", Query.Direction.DESCENDING)
            .limit(size.toLong())
            .get()
            .await()

        return snapshot.documents
            .map {
                User(
                    id = it.getString("id") ?: "",
                    name = it.getString("name") ?: "",
                    avatarUrl = it.getString("avatarUrl") ?: ""
                )
            }
            .filter {
                it.id != excludeUserId
            }
    }

    override suspend fun uploadUsers(
        students: List<StudentSummary>
    ) {
        val batch = firestore.batch()

        students.forEach { student ->
            val docRef = firestore
                .collection("users")
                .document(student.studentCode.lowercase())

            batch.set(
                docRef,
                mapOf(
                    "id" to student.studentCode.lowercase(),
                    "name" to student.fullName,
                    "avatarUrl" to "",
                    "isOnline" to false,
                    "lastSeen" to 0L
                )
            )
        }

        batch.commit().await()
    }

    override suspend fun uploadUser(student: StudentSummary) {
        val studentId = student.studentCode.lowercase()
        val fcmToken = firebaseStorage.getFirebaseToken()

        val docRef = firestore
            .collection("users")
            .document(studentId)

        val snapshot = docRef.get().await()

        if (!snapshot.exists()) {
            docRef.set(
                mapOf(
                    "id" to studentId,
                    "name" to student.fullName,
                    "avatarUrl" to student.avatarUrl,
                    "fcmTokens" to listOf(fcmToken)
                )
            ).await()
        } else {
            docRef.update(
                mapOf(
                    "fcmTokens" to FieldValue.arrayUnion(fcmToken)
                )
            ).await()
        }
    }

    override fun observeUsers(
        size: Int,
        excludeUserId: String?
    ): Flow<List<User>> = callbackFlow {

        val listener = firestore
            .collection("users")
            .orderBy("isOnline", Query.Direction.DESCENDING)
            .limit(size.toLong())
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val users = snapshot?.documents
                    ?.map {
                        User(
                            id = it.getString("id") ?: "",
                            name = it.getString("name") ?: "",
                            avatarUrl = it.getString("avatarUrl") ?: ""
                        )
                    }
                    ?.filter {
                        it.id != excludeUserId
                    }
                    ?: emptyList()

                trySend(users)
            }

        awaitClose {
            listener.remove()
        }
    }
}