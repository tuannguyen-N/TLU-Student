package org.example.project.presentations.screen.setting

sealed interface SettingUiEvent {
    data object LogoutSuccessful : SettingUiEvent
    data object RequestNotificationPermission : SettingUiEvent
    data object OpenAppSettings : SettingUiEvent
    data class ShowToast(val message: String) : SettingUiEvent
}