package org.example.project.domain.model

data class LoginState(
    val accessToken: String? = null,
    val isLoading: Boolean = false
)
