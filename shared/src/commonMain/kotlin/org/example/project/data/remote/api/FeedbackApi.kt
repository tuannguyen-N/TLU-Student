package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.data.remote.dto.feedback.FeedbackCategoryResponse
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import org.example.project.data.remote.dto.Response

class FeedbackApi(
    private val client: HttpClient
) {
    suspend fun getCategory(): FeedbackCategoryResponse {
        return client.get("/api/v1/feedback/category").body()
    }

    suspend fun sendFeedback(
        files: List<Pair<String, ByteArray>>,
        title: String,
        categoryId: Long,
        content: String,
        appVersion: String? = null,
        deviceInfo: String? = null
    ): Response {
        return client.submitFormWithBinaryData(
            url = "/api/v1/feedback/send",
            formData = formData {
                files.forEach { (fileName, fileBytes) ->
                    append(
                        key = "files",
                        value = fileBytes,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "application/octet-stream")
                            append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                        }
                    )
                }
                append("title", title)
                append("categoryId", categoryId.toString())
                append("content", content)
                appVersion?.let { append("appVersion", it) }
                deviceInfo?.let { append("deviceInfo", it) }
            }
        ).body()
    }
}