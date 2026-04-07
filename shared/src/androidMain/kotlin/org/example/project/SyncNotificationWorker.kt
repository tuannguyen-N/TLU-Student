package org.example.project

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.example.MyApplication
import org.example.project.domain.repository.NotificationRepository

class SyncNotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val notificationRepository: NotificationRepository by lazy {
        (applicationContext as MyApplication).appContainer.notificationRepository
    }

    override suspend fun doWork(): Result {
        return notificationRepository.getNotifications(true).fold(
            onSuccess = { Result.success() },
            onFailure = { Result.retry() }
        )
    }
}