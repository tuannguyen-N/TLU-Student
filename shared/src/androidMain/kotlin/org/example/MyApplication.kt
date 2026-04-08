package org.example

import android.app.Application
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import org.example.project.di.AppContainer
import org.example.project.local.AndroidAppContainer

class MyApplication : Application() {
    lateinit var appContainer: AppContainer
    lateinit var androidAppContainer: AndroidAppContainer

    override fun onCreate() {
        super.onCreate()
        androidAppContainer = AndroidAppContainer(applicationContext)
        appContainer = AppContainer(
            androidAppContainer.tokenStorage,
            androidAppContainer.imageStorage,
            androidAppContainer.firebaseStorage,
            androidAppContainer.deviceProvider,
            androidAppContainer.appPreferences,
            androidAppContainer.topicSubscriber,
            applicationContext
        )
        handleFirebaseToken()
    }

    private fun handleFirebaseToken() {
        val oldToken = androidAppContainer.firebaseStorage.getFirebaseToken()
        FirebaseMessaging.getInstance().token.addOnSuccessListener { newToken ->
            Log.d("FCM", "Current Token: $newToken")
            if (newToken != oldToken) {
                androidAppContainer.firebaseStorage.saveFirebaseToken(newToken)
            }
        }
    }
}