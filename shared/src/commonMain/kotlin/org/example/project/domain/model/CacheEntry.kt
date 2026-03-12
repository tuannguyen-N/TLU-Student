package org.example.project.presentations.utils

private data class CacheEntry<T>(
    val data: T,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun isExpired(ttlMs: Long) = System.currentTimeMillis() - timestamp > ttlMs
}
