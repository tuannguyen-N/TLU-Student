package org.example.project.domain.repository

import org.example.project.data.mapper.toListNotificationUiModel
import org.example.project.data.remote.api.NotificationApi
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.NotificationUiModel

class NotificationRepository(
    private val notificationApi: NotificationApi
) {
    suspend fun getNotifications(): AppResult<List<NotificationUiModel>> {
        return try {
            val data = notificationApi.getNotifications().data ?: throw Exception("Thông báo trống")
            AppResult.Success(data.toListNotificationUiModel())
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }
}