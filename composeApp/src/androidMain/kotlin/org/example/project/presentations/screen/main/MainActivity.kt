package org.example.project.presentations.screen.main

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import org.example.MyApplication
import org.example.project.presentations.utils.MsalHelper
import org.example.project.presentations.utils.NetworkMonitor
import org.example.project.presentations.utils.PaymentDeepLinkEvent
import org.example.project.presentations.utils.createNotificationChannel

class MainActivity : ComponentActivity() {
    private val appContainer by lazy {
        (application as MyApplication).appContainer
    }

    private lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMsal()
        testFirestore()
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
        observeNetworkStatus()
        handleDeepLink(intent)
    }

    override fun onResume() {
        super.onResume()
        appContainer.notificationRepository.reconnectIfNeeded()
    }

    fun testFirestore() {
        FirebaseFirestore.getInstance()
            .collection("chatRooms")
            .get()
            .addOnSuccessListener {
                Log.d("FIREBASE", "Success: ${it.size()}")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", it.message ?: "")
            }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        val data = intent?.data ?: return
        if (data.scheme == "myapp" && data.host == "payment") {
            val responseCode = data.getQueryParameter("vnp_ResponseCode")
            val txnRef = data.getQueryParameter("vnp_TxnRef")

            PaymentDeepLinkEvent.emit(responseCode, txnRef)
        }
    }

    private fun observeNetworkStatus() {
        networkMonitor = NetworkMonitor(this)
        lifecycleScope.launch {
            networkMonitor.isConnected.collect { isConnected ->
                if (!isConnected) {
                    Toast.makeText(this@MainActivity, "Mất kết nối mạng!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initMsal() {
        MsalHelper.init(this) {
            setContent {
                CompositionLocalProvider(
                    LocalAppContainer provides appContainer
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