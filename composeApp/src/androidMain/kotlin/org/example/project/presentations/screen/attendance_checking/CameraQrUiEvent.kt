package org.example.project.presentations.screen.attendance_checking

sealed interface CameraQrUiEvent {
    object OnScanAndApiCompleted : CameraQrUiEvent
    object OnAttendanceSuccess : CameraQrUiEvent
    object OnAttendanceFailure : CameraQrUiEvent
}