package org.example

import android.app.Application
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import org.example.project.di.AppContainer
import org.example.project.local.AndroidAppContainer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.content.Context
import android.Manifest
import androidx.annotation.RequiresPermission
import org.example.project.data.remote.isNetworkAvailable

class MyApplication : Application() {
    lateinit var appContainer: AppContainer
    lateinit var androidAppContainer: AndroidAppContainer

    override fun onCreate() {
        super.onCreate()
        isNetworkAvailable = @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE) {
            val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val activeNetwork = connectivityManager?.activeNetwork
            val capabilities = connectivityManager?.getNetworkCapabilities(activeNetwork)
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        }
        
        androidAppContainer = AndroidAppContainer(applicationContext)
        appContainer = AppContainer(
            androidAppContainer.tokenStorage,
            androidAppContainer.imageStorage,
            androidAppContainer.firebaseStorage,
            androidAppContainer.deviceProvider,
            androidAppContainer.appPreferences,
            androidAppContainer.topicSubscriber,
            androidAppContainer.locationRepository,
            applicationContext,
            androidAppContainer.messageRepository
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