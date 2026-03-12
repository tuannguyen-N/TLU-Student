package org.example.project.domain.model

import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class CacheEntry<T>(
    val data: T,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds()
) {
    fun isExpired(ttl: Duration): Boolean {
        val now = Clock.System.now().toEpochMilliseconds()
        return (now - timestamp) > ttl.inWholeMilliseconds
    }
}