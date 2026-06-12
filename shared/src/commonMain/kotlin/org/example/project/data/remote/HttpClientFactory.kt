package org.example.project.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json
import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.dto.login.RefreshTokenRequest
import org.example.project.data.remote.dto.login.RefreshTokenResponse

var isNetworkAvailable: () -> Boolean = { true }
val showNoNetworkDialog = MutableStateFlow(false)

fun HttpClient.clearBearerTokens() {
    authProvider<BearerAuthProvider>()?.clearToken()
}

val NetworkCheckPlugin = createClientPlugin("NetworkCheckPlugin") {
    onRequest { _, _ ->
        if (!isNetworkAvailable()) {
            showNoNetworkDialog.value = true
            throw CancellationException("No network connection")
        }
    }
}

fun createHttpClient(tokenStorage: TokenStorage, triggerLogout: () -> Unit): HttpClient {
    return HttpClient {
        install(NetworkCheckPlugin)

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "tl-connect-app-latest.onrender.com"
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

        install(Auth) {
            bearer {
                loadTokens {
                    val accessToken = tokenStorage.getAccessToken()
                    val refreshToken = tokenStorage.getRefreshToken()
                    if (accessToken != null && refreshToken != null) {
                        BearerTokens(accessToken, refreshToken)
                    } else {
                        null
                    }
                }
                refreshTokens {
                    val oldAccessToken = tokenStorage.getAccessToken() ?: ""
                    val oldRefreshToken =
                        tokenStorage.getRefreshToken() ?: return@refreshTokens null
                    try {
                        val response =
                            client.post("https://tl-connect-app-latest.onrender.com/api/v1/oauth2/refresh") {
                                markAsRefreshTokenRequest()
                                header("Authorization", "Bearer $oldAccessToken")
                                contentType(ContentType.Application.Json)
                                setBody(RefreshTokenRequest(refreshToken = oldRefreshToken))
                            }.body<RefreshTokenResponse>()

                        if (response.code == 0 && response.data != null) {
                            val newAccessToken = response.data.accessToken
                            val newRefreshToken = response.data.refreshToken

                            tokenStorage.saveAccessToken(newAccessToken)
                            tokenStorage.saveRefreshToken(newRefreshToken)

                            BearerTokens(newAccessToken, newRefreshToken)
                        } else {
                            tokenStorage.clearAccessToken()
                            tokenStorage.clearRefreshToken()
                            triggerLogout()
                            null
                        }
                    } catch (e: Exception) {
                        null
                    }
                }
            }
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

fun createExternalHttpClientWithAuthPlugin(tokenStorage: TokenStorage, triggerLogout: () -> Unit): HttpClient {
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

        install(Auth) {
            bearer {
                loadTokens {
                    val accessToken = tokenStorage.getAccessToken()
                    val refreshToken = tokenStorage.getRefreshToken()
                    if (accessToken != null && refreshToken != null) {
                        BearerTokens(accessToken, refreshToken)
                    } else {
                        null
                    }
                }
                refreshTokens {
                    val oldAccessToken = tokenStorage.getAccessToken() ?: ""
                    val oldRefreshToken =
                        tokenStorage.getRefreshToken() ?: return@refreshTokens null
                    try {
                        val response =
                            client.post("https://tl-connect-app-latest.onrender.com/api/v1/oauth2/refresh") {
                                markAsRefreshTokenRequest()
                                header("Authorization", "Bearer $oldAccessToken")
                                contentType(ContentType.Application.Json)
                                setBody(RefreshTokenRequest(refreshToken = oldRefreshToken))
                            }.body<RefreshTokenResponse>()

                        if (response.code == 0 && response.data != null) {
                            val newAccessToken = response.data.accessToken
                            val newRefreshToken = response.data.refreshToken

                            tokenStorage.saveAccessToken(newAccessToken)
                            tokenStorage.saveRefreshToken(newRefreshToken)

                            BearerTokens(newAccessToken, newRefreshToken)
                        } else {
                            tokenStorage.clearAccessToken()
                            tokenStorage.clearRefreshToken()
                            triggerLogout()
                            null
                        }
                    } catch (e: Exception) {
                        null
                    }
                }
            }
        }
    }
}