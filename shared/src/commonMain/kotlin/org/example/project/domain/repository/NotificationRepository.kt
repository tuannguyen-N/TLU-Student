package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.example.project.data.cache.CacheManager
import org.example.project.data.local.FirebaseStorage
import org.example.project.data.local.dao.NotificationDao
import org.example.project.data.mapper.toEntity
import org.example.project.data.mapper.toListNotificationUiModel
import org.example.project.data.remote.api.NotificationApi
import org.example.project.data.remote.dto.notification.MarkReadNotificationResponse
import org.example.project.data.remote.dto.notification.NotificationData
import org.example.project.data.remote.dto.notification_prepare.PrepareNotificationData
import org.example.project.domain.TopicSubscriber
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.NotificationUiModel
import kotlin.time.Duration.Companion.minutes

class NotificationRepository(
    private val notificationApi: NotificationApi,
    private val firebaseStorage: FirebaseStorage,
    private val topicSubscriber: TopicSubscriber,
    private val notificationDao: NotificationDao
) {
    private lateinit var prepareNotificationData: PrepareNotificationData

    private val _readNotificationIds = notificationDao.observeReadNotifications()
    private val notificationCache = CacheManager<String, NotificationData>(5.minutes)
    private val _notifications = MutableStateFlow<List<NotificationUiModel>>(emptyList())
    val notifications = combine(_notifications, _readNotificationIds) { notifications, readIds ->
        val readIdsSet = readIds.toSet()
        notifications.map {
            it.copy(isRead = readIdsSet.contains(it.id))
        }
    }

    suspend fun getNotifications(forceRefresh: Boolean = false): AppResult<List<NotificationUiModel>> {
        return try {
            val data =
                notificationCache.getOrFetch(key = "notification", forceRefresh = forceRefresh) {
                    notificationApi.getNotifications(
                        prepareNotificationData.oauthUserId,
                        prepareNotificationData.facultyId,
                        prepareNotificationData.studentClassId
                    ).data ?: throw Exception("Thông báo trống")
                }
            val newList = data.toListNotificationUiModel()
            val readIds = notificationDao.getReadNotifications().map { it.id }.toSet()
            val mergedList = newList.map {
                if (it.id in readIds) it.copy(isRead = true) else it
            }

            _notifications.update { currentList ->
                val currentIds = currentList.map { it.id }.toSet()
                val incoming = mergedList.filter { it.id !in currentIds }
                incoming + currentList
            }

            AppResult.Success(mergedList)
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }

    suspend fun prepareNotification(): AppResult<PrepareNotificationData> {
        return try {
            val data = notificationApi.prepareNotification().data
                ?: throw Exception("Lỗi khi chuẩn bị thông báo")

            prepareNotificationData = data

            val newTopics = data.topics
            val oldTopics = firebaseStorage.getTopics()

            val toSubscribe = newTopics - oldTopics.toSet()
            val toUnsubscribe = oldTopics - newTopics.toSet()

            topicSubscriber.subscribe(toSubscribe)
            topicSubscriber.unsubscribe(toUnsubscribe)

            firebaseStorage.saveTopics(newTopics)

            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }

    suspend fun markReadNotification(notificationIds: List<Int>): AppResult<MarkReadNotificationResponse> {
        return try {
            val data = notificationApi.markReadNotification(notificationIds)
            return AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }

    suspend fun insertReadNotification(notification: NotificationUiModel) {
        notificationDao.insertReedNotification(notification.toEntity())
    }

    suspend fun insertReadNotifications(notifications: List<NotificationUiModel>) {
        notificationDao.insertReedNotifications(notifications.map { it.toEntity() })
    }

    suspend fun getReadNotifications(): List<Int> {
        return notificationDao.getReadNotifications().map { it.id }
    }
}