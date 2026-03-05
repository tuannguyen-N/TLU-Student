package org.example.project.data.remote.interceptor

import io.ktor.client.plugins.api.createClientPlugin
import org.example.project.data.local.TokenStorage

class AuthPluginConfig {
    lateinit var tokenStorage: TokenStorage
}

val AuthPlugin = createClientPlugin(
    name = "AuthPlugin",
    createConfiguration = ::AuthPluginConfig
) {
    val tokenStorage = pluginConfig.tokenStorage
    onRequest { request, _ ->
        val token = tokenStorage.getAccessToken()
        if (!token.isNullOrBlank()) {
            request.headers["Authorization"] = "Bearer $token"
        }
    }
}