package org.example.project.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.example.project.data.local.TokenStorage
import androidx.core.content.edit

class AndroidTokenStorage(
    private val context: Context
) : TokenStorage {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
    }

    private val sharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun saveAccessToken(token: String) {
        sharedPreferences.edit {
            putString(KEY_ACCESS_TOKEN, token)
        }
    }

    override fun getAccessToken(): String? =
        sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    override fun clearAccessToken(){
        sharedPreferences.edit {
            remove(KEY_ACCESS_TOKEN)
        }
    }
}