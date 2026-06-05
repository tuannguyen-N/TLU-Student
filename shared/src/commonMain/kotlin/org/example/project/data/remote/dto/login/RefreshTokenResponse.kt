package org.example.project.data.remote.dto.login

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val code: Int,
    val message: String,
    val data: RefreshTokenData?
)

@Serializable
data class RefreshTokenData(
    val accessToken: String,
    val refreshToken: String
)
