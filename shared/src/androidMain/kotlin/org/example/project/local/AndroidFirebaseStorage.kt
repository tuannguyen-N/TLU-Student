package org.example.project.local

import android.content.SharedPreferences
import androidx.core.content.edit
import org.example.project.data.local.FirebaseStorage
import org.json.JSONArray

class AndroidFirebaseStorage(
    private val encryptedSharedPreferences: SharedPreferences
) : FirebaseStorage {
    companion object {
        private const val KEY_FIREBASE_TOKEN = "FIREBASE_TOKEN"
        private const val KEY_TOPICS = "KEY_TOPICS"
    }

    override fun saveFirebaseToken(token: String) {
        encryptedSharedPreferences.edit {
            putString(KEY_FIREBASE_TOKEN, token)
        }
    }

    override fun getFirebaseToken(): String? =
        encryptedSharedPreferences.getString(KEY_FIREBASE_TOKEN, null)

    override fun saveTopics(topics: List<String>) {
        encryptedSharedPreferences.edit {
            putStringSet(KEY_TOPICS, topics.toSet())
        }
    }

    override fun getTopics(): List<String> {
        return encryptedSharedPreferences
            .getStringSet(KEY_TOPICS, emptySet())
            ?.toList()
            ?: emptyList()
    }

    override fun clearTopic(topic: String) {
        val set = getTopics().toMutableSet()
        set.remove(topic)

        encryptedSharedPreferences.edit {
            putStringSet(KEY_TOPICS, set)
        }
    }

    override fun clearAllTopics() {
        encryptedSharedPreferences.edit {
            remove(KEY_TOPICS)
        }
    }
}