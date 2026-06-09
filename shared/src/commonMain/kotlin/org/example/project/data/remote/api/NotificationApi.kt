package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.remote.dto.notification.MarkReadNotificationRequest
import org.example.project.data.remote.dto.notification.MarkReadNotificationResponse
import org.example.project.data.remote.dto.notification.NotificationRequest
import org.example.project.data.remote.dto.notification.NotificationResponse
import org.example.project.data.remote.dto.notification_detail.NotificationDetailResponse
import org.example.project.data.remote.dto.notification_prepare.PrepareNotificationResponse

class NotificationApi(
    private val client: HttpClient
) {
    suspend fun getNotifications(
        oauthUserId: Int,
        facultyId: Int,
        studentClassId: Int,
        page: Int,
        size: Int,
        search: String?
    ): NotificationResponse {
        return client.post("/api/v1/notification?size=$size&page=$page&search=$search") {
            contentType(ContentType.Application.Json)
            setBody(
                NotificationRequest(
                    oauthUserId = oauthUserId,
                    facultyId = facultyId,
                    studentClassId = studentClassId
                )
            )
        }.body()
    }

    suspend fun getNotificationDetail(id: Int): NotificationDetailResponse {
        return client.get("/api/v1/notification/detail/$id").body()
    }

    suspend fun prepareNotification(): PrepareNotificationResponse {
        return client.get("/api/v1/notification/prepare").body()
    }

    suspend fun markReadNotification(notificationIds: List<Int>): MarkReadNotificationResponse {
        return client.post("/api/v1/notification/read") {
            contentType(ContentType.Application.Json)
            setBody(
                MarkReadNotificationRequest(
                    notificationIds = notificationIds
                )
            )
        }.body()
    }
}