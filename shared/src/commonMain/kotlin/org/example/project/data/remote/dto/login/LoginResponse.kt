package org.example.project.data.remote.dto.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val code: Int,
    val message: String,
    val data: LoginDataResponse?
)