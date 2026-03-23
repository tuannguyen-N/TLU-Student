package org.example.project.presentations.screen.setting

sealed interface SettingUiEvent {
    object LogoutSuccessful : SettingUiEvent
}