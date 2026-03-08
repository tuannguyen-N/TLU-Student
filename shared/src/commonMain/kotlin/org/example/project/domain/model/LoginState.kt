package org.example.project.domain.model

data class LoginState(
    val showErrorSheet: Boolean = false,
    val isLoading: Boolean = false
)
