package org.example.project.data.cache

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration

class CacheManager<K, V>(
    private val ttl: Duration
) {
    private val mutex = Mutex()
    private val cache = mutableMapOf<K, CacheEntry<V>>()

    suspend fun getOrFetch(
        key: K,
        forceRefresh: Boolean = false,
        fetch: suspend () -> V
    ): V {
        return mutex.withLock {
            val entry = cache[key]
            if (!forceRefresh && entry != null && !entry.isExpired(ttl)) {
                return entry.data
            }

            val newData = fetch()
            cache[key] = CacheEntry(newData)
            newData
        }
    }

    fun invalidate(key: K){
        cache.remove(key)
    }

    fun clear(){
        cache.clear()
    }
}