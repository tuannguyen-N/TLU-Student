package org.example.project.local

import android.content.SharedPreferences
import androidx.core.content.edit
import org.example.project.data.local.TokenStorage

class AndroidTokenStorage(
    private val encryptedSharedPreferences: SharedPreferences
) : TokenStorage {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
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

    override fun saveRefreshToken(token: String) {
        encryptedSharedPreferences.edit {
            putString(KEY_REFRESH_TOKEN, token)
        }
    }

    override fun getRefreshToken(): String? =
        encryptedSharedPreferences.getString(KEY_REFRESH_TOKEN, null)

    override fun clearRefreshToken() {
        encryptedSharedPreferences.edit {
            remove(KEY_REFRESH_TOKEN)
        }
    }
}