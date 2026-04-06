package org.example.project.data.local

interface FirebaseStorage {
    fun saveFirebaseToken(token: String)
    fun getFirebaseToken(): String?
}