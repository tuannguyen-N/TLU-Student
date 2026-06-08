package org.example.project.domain.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.example.project.data.local.FirebaseStorage
import org.example.project.data.local.dao.AlertDao
import org.example.project.data.local.dao.MarkedNotificationDao
import org.example.project.data.local.dao.NotificationDao
import org.example.project.data.local.entity.PerformedAlertEntity
import org.example.project.data.mapper.toAlertUiModels
import org.example.project.data.mapper.toEntity
import org.example.project.data.mapper.toMarkedEntity
import org.example.project.data.mapper.toUiModel
import org.example.project.data.remote.api.NotificationApi
import org.example.project.data.remote.api.NotificationSocket
import org.example.project.data.remote.dto.notification.MarkReadNotificationResponse
import org.example.project.data.remote.dto.notification_detail.NotificationDetailData
import org.example.project.data.remote.dto.notification_prepare.PrepareNotificationData
import org.example.project.domain.TopicSubscriber
import org.example.project.domain.model.AlertUiModel
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.NotificationUiModel

class NotificationRepository(
    private val notificationApi: NotificationApi,
    private val firebaseStorage: FirebaseStorage,
    private val topicSubscriber: TopicSubscriber,
    private val markedNotificationDao: MarkedNotificationDao,
    private val notificationDao: NotificationDao,
    private val alertDao: AlertDao,
    private val notificationSocket: NotificationSocket
) {
    private lateinit var prepareNotificationData: PrepareNotificationData

    private var isLastPage = false
    val readNotificationIds = markedNotificationDao.observeReadNotifications()

    val notifications = combine(
        notificationDao.observeNotifications(),
        readNotificationIds
    ) { notifications, readIds ->
        val readSet = readIds.toSet()
        notifications.map {
            it.toUiModel().copy(isRead = it.id in readSet)
        }
    }

    val hasUnreadNotifications = notifications.map { list ->
        list.any { !it.isRead }
    }

    private var realtimeJob: Job? = null

    suspend fun getNotifications(
        forceRefresh: Boolean = false,
        page: Int = 0,
        size: Int = 15
    ): AppResult<Boolean> {
        return try {
            if (forceRefresh) {
                isLastPage = false
            }

            if (isLastPage) return AppResult.Success(false)

            val data = notificationApi.getNotifications(
                oauthUserId = prepareNotificationData.oauthUserId,
                facultyId = prepareNotificationData.facultyId,
                studentClassId = prepareNotificationData.studentClassId,
                page = page,
                size = size
            ).data ?: error("Empty")

            if (forceRefresh && page == 0) {
                notificationDao.clearNotifications()
            }

            notificationDao.insertNotifications(
                data.content.map { it.toEntity() }
            )

            isLastPage = data.last
            AppResult.Success(!isLastPage)

        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }

    fun startRealtime() {
        if (realtimeJob?.isActive == true) return
        realtimeJob = CoroutineScope(
            SupervisorJob() + Dispatchers.Default
        ).launch {
            notificationSocket.connect()

            notificationSocket.subscribe(
                "/topic/notification/global"
            )

            notificationSocket.subscribe(
                "/topic/notification/faculty/${prepareNotificationData.facultyId}"
            )

            notificationSocket.subscribe(
                "/topic/notification/class/${prepareNotificationData.studentClassId}"
            )

            notificationSocket.subscribe(
                "/user/queue/notification"
            )

            notificationSocket.notifications()
                .collect { payload ->
                    notificationDao.insertNotification(
                        payload.toEntity()
                    )
                }
        }
    }

    suspend fun stopRealtime() {
        realtimeJob?.cancel()
        realtimeJob = null
        notificationSocket.disconnect()
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
        markedNotificationDao.insertReadNotification(notification.toMarkedEntity())
    }

    suspend fun insertReadNotifications(notifications: List<NotificationUiModel>) {
        markedNotificationDao.insertReadNotifications(notifications.map { it.toMarkedEntity() })
    }

    suspend fun getReadNotifications(): List<Int> {
        return markedNotificationDao.getReadNotifications().map { it.id }
    }

    suspend fun getNotificationDetail(id: Int): AppResult<NotificationDetailData> {
        return try {
            val data =
                notificationApi.getNotificationDetail(id).data ?: throw Exception("Thông báo trống")
            AppResult.Success(data)
        } catch (e: Exception) {
            return AppResult.Failure(e.message, e)
        }
    }

    fun getAlertList(studentId: String): Flow<List<AlertUiModel>> {
        return combine(
            notifications,
            alertDao.observePerformedAlerts(studentId)
        ) { notificationList, performedAlerts ->

            val performedIds = performedAlerts
                .map { it.notificationId }
                .toSet()

            notificationList
                .filterNot { it.id in performedIds }
                .toAlertUiModels()
                .sortedWith(
                    compareBy { it.daysUntil }
                )
        }.distinctUntilChanged()
    }

    fun getFullAlertList(): Flow<List<AlertUiModel>> {
        return notifications.map { it.toAlertUiModels() }
    }

    suspend fun getPerformedAlerts(studentId: String): List<PerformedAlertEntity> {
        return alertDao.getAllPerformedAlerts(studentId = studentId)
    }

    suspend fun insertPerformedAlert(studentId: String, notificationId: Int) {
        alertDao.insertPerformedAlert(PerformedAlertEntity(studentId, notificationId))
    }

    suspend fun deletePerformedAlert(studentId: String, notificationId: Int) {
        alertDao.deletePerformedAlert(notificationId, studentId)
    }
}