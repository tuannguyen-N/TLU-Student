package org.example.project.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.interceptor.AuthPlugin

fun createHttpClient(tokenStorage: TokenStorage): HttpClient {
    return HttpClient {

        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                host = "localhost"
                port = 8080
                path("api/v1/")
            }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }

        install(AuthPlugin) {
            this.tokenStorage = tokenStorage
        }
    }
}