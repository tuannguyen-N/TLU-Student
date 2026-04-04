package org.example.project.local

import android.content.SharedPreferences
import androidx.core.content.edit
import org.example.project.data.local.ImageBase64Storage

class AndroidImageBase64Storage(
    private val encryptedSharedPreferences: SharedPreferences
) : ImageBase64Storage {
    companion object {
        private const val KEY_IMAGE_BASE_64 = "IMAGE_BASE_64"
    }

    override fun saveImageBase64(imageBase64: String?) {
        encryptedSharedPreferences.edit {
            putString(KEY_IMAGE_BASE_64, imageBase64)
        }
    }

    override fun getImageBase64(): String? =
        encryptedSharedPreferences.getString(KEY_IMAGE_BASE_64, null)


    override fun clearImageBase64() {
        encryptedSharedPreferences.edit {
            remove(KEY_IMAGE_BASE_64)
        }
    }
}