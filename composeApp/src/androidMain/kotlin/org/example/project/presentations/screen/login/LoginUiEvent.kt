package org.example.project.presentations.screen.login

sealed interface LoginUiEvent {
    object OnNavigateToHome : LoginUiEvent
    object ShowError : LoginUiEvent
}
