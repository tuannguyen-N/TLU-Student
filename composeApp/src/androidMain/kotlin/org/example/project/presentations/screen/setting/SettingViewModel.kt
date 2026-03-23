package org.example.project.presentations.screen.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.LogoutUseCase
import org.example.project.presentations.utils.MsalHelper

class SettingViewModel(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _event = Channel<SettingUiEvent>()
    val event = _event.receiveAsFlow()

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
}