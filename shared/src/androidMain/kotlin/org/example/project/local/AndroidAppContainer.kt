package org.example.project.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.example.project.AndroidMessageRepository
import org.example.project.AndroidPresenceRepository
import org.example.project.AndroidSearchHistoryRepository
import org.example.project.AndroidTopicSubscriber
import org.example.project.AndroidUserRepository
import org.example.project.DeviceProvider
import org.example.project.data.AndroidLocationRepository
import org.example.project.data.local.AppPreferences
import org.example.project.data.local.FirebaseStorage
import org.example.project.data.local.ImageBase64Storage
import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.api.FileUploadApi
import org.example.project.data.remote.createHttpClient

class AndroidAppContainer(context: Context) {
    private val encryptedSharedPreferences: SharedPreferences by lazy {
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

    val tokenStorage: TokenStorage by lazy {
        AndroidTokenStorage(encryptedSharedPreferences)
    }

    private val httpClient = createHttpClient(tokenStorage)

    val imageStorage: ImageBase64Storage by lazy {
        AndroidImageBase64Storage(encryptedSharedPreferences)
    }

    val firebaseStorage: FirebaseStorage by lazy {
        AndroidFirebaseStorage(encryptedSharedPreferences)
    }

    val deviceProvider = DeviceProvider(context)

    val topicSubscriber = AndroidTopicSubscriber()

    val locationRepository = AndroidLocationRepository(context)

    val appPreferences: AppPreferences by lazy {
        AndroidAppPreferences(encryptedSharedPreferences)
    }

    val messageRepository = AndroidMessageRepository(
        fileUploadApi = FileUploadApi(httpClient)
    )

    val userRepository = AndroidUserRepository()

    val presenceRepository = AndroidPresenceRepository()

    val searchHistoryRepository = AndroidSearchHistoryRepository()
}