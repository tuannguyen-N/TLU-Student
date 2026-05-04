package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.data.remote.dto.application.ApplicationTypesResponse

class ApplicationApi(
    private val client: HttpClient
) {
    suspend fun getApplicationTypes(): ApplicationTypesResponse {
        return client.get("/api/v1/applications/types").body()
    }


}