package org.example.project.local

import android.content.SharedPreferences
import androidx.core.content.edit
import org.example.project.data.local.FirebaseStorage

class AndroidFirebaseStorage(
    private val encryptedSharedPreferences: SharedPreferences
) : FirebaseStorage {
    companion object {
        private const val KEY_FIREBASE_TOKEN = "FIREBASE_TOKEN"
    }

    override fun saveFirebaseToken(token: String) {
        encryptedSharedPreferences.edit {
            putString(KEY_FIREBASE_TOKEN, token)
        }
    }

    override fun getFirebaseToken(): String? =
        encryptedSharedPreferences.getString(KEY_FIREBASE_TOKEN, null)
}