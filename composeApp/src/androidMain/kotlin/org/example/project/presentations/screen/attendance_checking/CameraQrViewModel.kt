package org.example.project.presentations.screen.attendance_checking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.repository.AttendanceRepository
import org.example.project.domain.repository.LocationRepository
import org.example.project.domain.usecase.GetLocationUseCase

class CameraQrViewModel(
    private val attendanceRepository: AttendanceRepository,
    private val getLocationUseCase: GetLocationUseCase,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CameraQrState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<CameraQrUiEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun onQrScanned(rawValue: String) {
        if (_uiState.value.isLoading || _uiState.value.isSuccess || _uiState.value.isError) return

        val location = getLocationUseCase()

        viewModelScope.launch {
            updateState { copy(isLoading = true, scannedValue = rawValue) }

            attendanceRepository.checkIn(rawValue, location.latitude, location.longitude).fold(
                onSuccess = { message ->
                    updateState { copy(isLoading = false, isSuccess = true) }
                    sendUiEvent(CameraQrUiEvent.OnAttendanceSuccess(message))
                },
                onFailure = { failure ->
                    updateState {
                        copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = failure.message
                        )
                    }
                    sendUiEvent(
                        CameraQrUiEvent.OnAttendanceFailure(
                            failure.message ?: "Đã xảy ra lỗi"
                        )
                    )
                }
            )
        }
    }

    fun checkGpsEnabled(): Boolean {
        return locationRepository.isGpsEnabled()
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
