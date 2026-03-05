package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.remote.dto.LoginRequest
import org.example.project.data.remote.dto.LoginResponse

class AuthApi(
    private val client: HttpClient
) {
    suspend fun login(microsoftAccessToken: String): LoginResponse =
        client.post("/api/v1/oauth2/login"){
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(accessToken = microsoftAccessToken))
        }.body()
}