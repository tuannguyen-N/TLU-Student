package org.example.project.local

import android.content.SharedPreferences
import androidx.core.content.edit
import org.example.project.data.local.TokenStorage

class AndroidTokenStorage(
    private val encryptedSharedPreferences: SharedPreferences
) : TokenStorage {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
    }

    override fun saveAccessToken(token: String) {
        encryptedSharedPreferences.edit {
            putString(KEY_ACCESS_TOKEN, token)
        }
    }

    override fun getAccessToken(): String? =
        encryptedSharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    override fun clearAccessToken(){
        encryptedSharedPreferences.edit {
            remove(KEY_ACCESS_TOKEN)
        }
    }
}