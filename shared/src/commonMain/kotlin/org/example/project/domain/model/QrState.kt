package org.example.project.domain.model


sealed interface QrState {
    object Idle: QrState
    object Generating: QrState
    data class Active(
        val qrData: String,
        val qrBitmap: ByteArray,
        val timeLeft: Int
    ): QrState
    data class Expired(val qrData: String): QrState
    data class Error(val message: String): QrState
}