package org.example.project.presentations.screen.attendance_checking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CameraQrViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CameraQrState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<CameraQrUiEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun onQrScanned(rawValue: String) {
        if (_uiState.value.isLoading || _uiState.value.isSuccess) return

        viewModelScope.launch {
            updateState { copy(isLoading = true, scannedValue = rawValue) }

            // TODO: Call API điểm danh ở đây
            // Tạm thời giả lập call API trong 1.5 giây
            try {
                delay(1500)
                updateState { copy(isLoading = false, isSuccess = true) }
                
                // Đợi 500ms để người dùng thấy trạng thái thành công (nếu cần) trước khi popBackStack
                sendUiEvent(CameraQrUiEvent.OnAttendanceSuccess)
            } catch (e: Exception) {
                updateState {
                    copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = e.message ?: "Có lỗi xảy ra khi điểm danh"
                    )
                }
            }
        }
    }

    fun dismissError() {
        updateState { copy(isError = false, errorMessage = null, scannedValue = null) }
    }

    fun resetState() {
        updateState { CameraQrState() }
    }

    private fun updateState(newState: CameraQrState.() -> CameraQrState) {
        _uiState.update(newState)
    }

    private fun sendUiEvent(event: CameraQrUiEvent) {
        _event.trySend(event)
    }
}
