package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.remote.dto.notification.NotificationRequest
import org.example.project.data.remote.dto.notification.NotificationResponse

class NotificationApi(
    private val client: HttpClient
) {
    suspend fun getNotifications(
        oauthUserId: Int = 1,
        facultyId: Int = 1,
        studentClassId: Int = 1
    ): NotificationResponse {
        return client.post("/api/v1/notification") {
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
}