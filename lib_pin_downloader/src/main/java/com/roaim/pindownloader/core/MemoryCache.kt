package com.roaim.pindownloader.core

import androidx.collection.LruCache

// Replaced MemoryCache with RoaimCache library
abstract class MemoryCache<T> {
    private var memoryCache: LruCache<String, T>
    // Get max available VM memory, exceeding this amount will throw an
    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
    // int in its constructor.
    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

    // Use 1/8th of the available memory for this memory cache.
    val cacheSize = maxMemory / 8

    init {
        memoryCache = object : LruCache<String, T>(cacheSize) {

            override fun sizeOf(key: String, content: T): Int {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return getContentLength(content) / 1024
            }

            override fun entryRemoved(evicted: Boolean, key: String, oldValue: T, newValue: T?) {
                onContentRemoved(key, oldValue)
                super.entryRemoved(evicted, key, oldValue, newValue)
            }
        }
    }

    abstract fun onContentRemoved(key: String, oldValue: T)

    abstract fun getContentLength(content: T): Int

    fun put(key: String, content: T?) {
        if (get(key) == null && content != null) memoryCache.put(key, content)
    }

    fun get(key: String): T? = memoryCache.get(key)
}