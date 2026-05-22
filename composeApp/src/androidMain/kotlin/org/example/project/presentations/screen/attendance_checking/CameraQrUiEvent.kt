package org.example.project.presentations.screen.attendance_checking

sealed interface CameraQrUiEvent {
    data object OnScanAndApiCompleted : CameraQrUiEvent
    data class OnAttendanceSuccess(val message: String) : CameraQrUiEvent
    data class OnAttendanceFailure(val message: String) : CameraQrUiEvent
}