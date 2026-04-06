package org.example.project.presentations.screen.main

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.firebase.messaging.FirebaseMessaging
import org.example.project.di.AppContainer
import org.example.project.local.AndroidAppContainer
import org.example.project.presentations.utils.MsalHelper
import org.example.project.presentations.utils.createNotificationChannel

class MainActivity : ComponentActivity() {
    lateinit var container: AppContainer
    lateinit var androidAppContainer: AndroidAppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppContainer()
        initMsal()
        handleFirebaseToken()
        fitSystemWindow()
        hideBottonNavigationBar()
        createNotificationChannel(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1001
            )
        }
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

    private fun initAppContainer() {
        androidAppContainer = AndroidAppContainer(applicationContext)
        container = AppContainer(
            androidAppContainer.tokenStorage,
            androidAppContainer.imageStorage,
            androidAppContainer.firebaseStorage,
            androidAppContainer.deviceProvider,
            applicationContext
        )
    }

    private fun initMsal() {
        MsalHelper.init(this) {
            setContent {
                CompositionLocalProvider(
                    LocalAppContainer provides container
                ) {
                    AppRoot(
                        resetAppData = {
                            restartApp()
                        }
                    )
                }
            }
        }
    }

    private fun restartApp() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    private fun fitSystemWindow() {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun hideBottonNavigationBar() {
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.navigationBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    }
}