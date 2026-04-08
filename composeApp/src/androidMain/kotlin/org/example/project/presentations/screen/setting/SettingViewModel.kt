package org.example.project.presentations.screen.setting

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.local.AppPreferences
import org.example.project.domain.usecase.LogoutUseCase
import org.example.project.presentations.utils.MsalHelper
import org.example.project.presentations.utils.NotificationPermissionManager

class SettingViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val permissionManager: NotificationPermissionManager,
    private val prefs: AppPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(
        SettingState(
            isNotificationEnabled = permissionManager.isGranted()
        )
    )
    val state = _state.asStateFlow()

    private val _event = Channel<SettingUiEvent>()
    val event = _event.receiveAsFlow()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun onToggleNotification(activity: Activity, enabled: Boolean) {
        if (enabled) {
            if (permissionManager.isGranted()) {
                _state.update { it.copy(isNotificationEnabled = true) }
                return
            }

            val shouldShow = permissionManager.shouldShowRationale(activity)
            val wasAsked = prefs.isNotificationPermissionAsked()

            when {
                !wasAsked -> {
                    prefs.setNotificationPermissionAsked(true)
                    sendEvent(SettingUiEvent.RequestNotificationPermission)
                }

                shouldShow -> {
                    sendEvent(SettingUiEvent.RequestNotificationPermission)
                }

                else -> {
                    sendEvent(SettingUiEvent.ShowToast("Vui lòng bật quyền trong cài đặt"))
                    sendEvent(SettingUiEvent.OpenAppSettings)
                }
            }
        } else {
            _state.update { it.copy(isNotificationEnabled = false) }
        }
    }

    fun onPermissionResult(granted: Boolean) {
        _state.update { it.copy(isNotificationEnabled = granted) }
    }

    fun logout() {
        MsalHelper.signOut { isSuccess ->
            if (isSuccess) {
                viewModelScope.launch {
                    logoutUseCase.signOut()
                    _event.trySend(SettingUiEvent.LogoutSuccessful)
                }
            } else {
                Log.e("SettingViewModel", "logout: error logout")
            }
        }
    }

    private fun sendEvent(event: SettingUiEvent) {
        viewModelScope.launch {
            _event.send(event)
        }
    }
}