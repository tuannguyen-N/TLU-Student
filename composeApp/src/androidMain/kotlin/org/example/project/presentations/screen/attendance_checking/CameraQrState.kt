package org.example.project.presentations.screen.attendance_checking

data class CameraQrState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val scannedValue: String? = null
)
