package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import org.example.project.data.remote.dto.application.ApplicationSubmitResponse
import org.example.project.data.remote.dto.application.ApplicationTypesResponse
import org.example.project.data.remote.dto.application_history.ApplicationHistoryResponse

class ApplicationApi(
    private val client: HttpClient
) {
    suspend fun getApplicationTypes(): ApplicationTypesResponse {
        return client.get("/api/v1/applications/types").body()
    }

    suspend fun submitApplication(
        files: List<Pair<String, ByteArray>>,
        applicationType: Int,
        content: String? = null
    ): ApplicationSubmitResponse {
        return client.submitFormWithBinaryData(
            url = "/api/v1/applications/submit",
            formData = formData {
                files.forEach { (fileName, fileBytes) ->
                    append(
                        key = "file",
                        value = fileBytes,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "application/octet-stream")
                            append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                        }
                    )
                }
                append("application-type", applicationType.toString())
                content?.let { append("content", it) }
            }
        ).body()
    }

    suspend fun getApplicationHistory(): ApplicationHistoryResponse {
        return client.get("/api/v1/applications/history").body()
    }

    suspend fun getApplicationDetail(id: Int): org.example.project.data.remote.dto.application_detail.ApplicationDetailResponse {
        return client.get("/api/v1/applications/history/$id").body()
    }
}