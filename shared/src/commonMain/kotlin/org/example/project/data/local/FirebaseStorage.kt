package org.example.project.data.local

interface FirebaseStorage {
    fun saveFirebaseToken(token: String)
    fun getFirebaseToken(): String?
    fun saveTopics(topics: List<String>)
    fun getTopics(): List<String>
    fun clearTopic(topic: String)
    fun clearAllTopics()
}