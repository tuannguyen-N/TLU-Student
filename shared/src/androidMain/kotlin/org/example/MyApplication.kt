package org.example

import android.Manifest
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.messaging.FirebaseMessaging
import org.example.project.data.remote.isNetworkAvailable
import org.example.project.di.AppContainer
import org.example.project.local.AndroidAppContainer
import org.example.project.local.AndroidLifecycleObserver

class MyApplication : Application() {
    lateinit var appContainer: AppContainer
    lateinit var androidAppContainer: AndroidAppContainer

    override fun onCreate() {
        super.onCreate()
        isNetworkAvailable = @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE) {
            val connectivityManager =
                applicationContext.getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager
            val activeNetwork = connectivityManager?.activeNetwork
            val capabilities = connectivityManager?.getNetworkCapabilities(activeNetwork)
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        }

        androidAppContainer = AndroidAppContainer(applicationContext, triggerLogout = {

        })
        appContainer = AppContainer(
            androidAppContainer.tokenStorage,
            androidAppContainer.imageStorage,
            androidAppContainer.firebaseStorage,
            androidAppContainer.deviceProvider,
            androidAppContainer.appPreferences,
            androidAppContainer.topicSubscriber,
            androidAppContainer.locationRepository,
            applicationContext,
            androidAppContainer.messageRepository,
            androidAppContainer.userRepository,
            androidAppContainer.searchHistoryRepository,
            androidAppContainer.presenceRepository,
            androidAppContainer.notificationSocket,
            androidAppContainer.paymentSocket,
            onClearAuthCache = { androidAppContainer.clearAuthCache() }
        )
        handleFirebaseToken()

        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(
                AndroidLifecycleObserver(
                    androidAppContainer.userRepository,
                    appContainer.studentUseCase,
                    androidAppContainer.presenceRepository
                )
            )
    }

    private fun handleFirebaseToken() {
        val oldToken = androidAppContainer.firebaseStorage.getFirebaseToken()
        FirebaseMessaging.getInstance().token.addOnSuccessListener { newToken ->
            Log.d("FCM", "Current Token: $newToken")
            if (newToken != oldToken) {
                androidAppContainer.firebaseStorage.saveFirebaseToken(newToken)
                // TODO: update in firestore 
            }
        }
    }
}