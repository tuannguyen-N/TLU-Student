package org.example.project

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import org.example.project.domain.repository.SearchHistoryRepository

class AndroidSearchHistoryRepository : SearchHistoryRepository {
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun saveSearchHistory(
        userId: String,
        keyword: String
    ) {
        if (keyword.isBlank()) return
        firestore
            .collection("users")
            .document(userId)
            .collection("searchHistory")
            .document(keyword.lowercase())
            .set(
                mapOf(
                    "keyword" to keyword,
                    "searchedAt" to System.currentTimeMillis()
                )
            )
            .await()
    }

    override suspend fun getSearchHistory(
        userId: String
    ): List<String> {
        return firestore
            .collection("users")
            .document(userId)
            .collection("searchHistory")
            .orderBy("searchedAt", Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .await()
            .documents
            .mapNotNull {
                it.getString("keyword")
            }
    }

    override fun observeSearchHistory(
        userId: String
    ): Flow<List<String>> = callbackFlow {

        val listener = firestore
            .collection("users")
            .document(userId)
            .collection("searchHistory")
            .orderBy("searchedAt", Query.Direction.DESCENDING)
            .limit(10)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val histories =
                    snapshot?.documents?.mapNotNull { it.getString("keyword") } ?: emptyList()

                trySend(histories)
            }

        awaitClose {
            listener.remove()
        }
    }

    override suspend fun removeSearchHistory(
        userId: String,
        keyword: String
    ) {
        firestore
            .collection("users")
            .document(userId)
            .collection("searchHistory")
            .document(keyword.lowercase())
            .delete()
            .await()
    }

    override suspend fun clearSearchHistory(
        userId: String
    ) {
        val documents = firestore
            .collection("users")
            .document(userId)
            .collection("searchHistory")
            .get()
            .await()

        val batch = firestore.batch()
        documents.documents.forEach {
            batch.delete(it.reference)
        }
        batch.commit().await()
    }
}