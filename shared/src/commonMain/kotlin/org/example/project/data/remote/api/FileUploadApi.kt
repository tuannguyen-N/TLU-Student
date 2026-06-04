package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import org.example.project.data.remote.dto.upload_image.UploadImageResponse

class FileUploadApi(
    private val client: HttpClient
) {
    suspend fun uploadFile(
        fileName: String,
        fileBytes: ByteArray
    ): UploadImageResponse {
        return client.submitFormWithBinaryData(
            url = "/api/v1/chat/upload",
            formData = formData {
                append(
                    key = "file",
                    value = fileBytes,
                    headers = Headers.build {
                        append(
                            HttpHeaders.ContentType,
                            "application/octet-stream"
                        )
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=\"$fileName\""
                        )
                    }
                )
            }
        ).body()
    }
}