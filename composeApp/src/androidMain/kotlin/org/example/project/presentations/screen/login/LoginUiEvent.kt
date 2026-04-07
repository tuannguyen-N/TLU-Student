package org.example.project.presentations.screen.login

sealed interface LoginUiEvent {
    object OnLoginSuccess : LoginUiEvent
    object ShowNoInternetDialog : LoginUiEvent
}
