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
import io.ktor.client.plugins.api.createClientPlugin
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow

var isNetworkAvailable: () -> Boolean = { true }
val showNoNetworkDialog = MutableStateFlow(false)

val NetworkCheckPlugin = createClientPlugin("NetworkCheckPlugin") {
    onRequest { _, _ ->
        if (!isNetworkAvailable()) {
            showNoNetworkDialog.value = true
            throw CancellationException("No network connection")
        }
    }
}

fun createHttpClient(tokenStorage: TokenStorage): HttpClient {
    return HttpClient {
        install(NetworkCheckPlugin)

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

fun createExternalHttpClient(): HttpClient {
    return HttpClient {
        install(NetworkCheckPlugin)

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }
}

fun createExternalHttpClientWithAuthPlugin(tokenStorage: TokenStorage): HttpClient{
    return HttpClient {
        install(NetworkCheckPlugin)

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