package org.example.project

import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.example.project.presentations.utils.ChatPresenceManager

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e("get_notification", "onMessageReceived: $message", )
        val incomingRoomId = message.data["roomId"]

        if (incomingRoomId != null && incomingRoomId == ChatPresenceManager.currentRoom.value) {
            return
        }

        val title = message.data["title"]
        val body = message.data["body"]
        showNotification(title, body)

//        val work = OneTimeWorkRequestBuilder<SyncNotificationWorker>().build()
//        WorkManager.getInstance(applicationContext).enqueue(work)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Token: $token")
    }

    fun showNotification(title: String?, message: String?) {
        val channelId = "default_channel"

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.icon_notification)
            .build()

        notificationManager.notify(0, notification)
    }
}