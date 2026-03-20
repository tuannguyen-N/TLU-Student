package org.example.project.domain.model

import kotlin.time.Clock

data class QrCode(
    val content: String,
    val generateAt: Long
){
    companion object {
        const val DURATION_SECONDS = 120
    }

    fun expiredAt(): Long = generateAt + (DURATION_SECONDS * 1000L)

    fun isExpired(): Boolean = expiredAt() < Clock.System.now().toEpochMilliseconds()
}
