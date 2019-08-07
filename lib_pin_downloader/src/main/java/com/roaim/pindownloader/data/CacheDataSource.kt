package com.roaim.pindownloader.data

import androidx.collection.LruCache

abstract class CacheDataSource<T> {
    private var memoryCache: LruCache<String, T>
    // Get max available VM memory, exceeding this amount will throw an
    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
    // int in its constructor.
    val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

    // Use 1/8th of the available memory for this memory cache.
    val cacheSize = maxMemory / 8

    init {
        memoryCache = object : LruCache<String, T>(cacheSize) {

            override fun sizeOf(key: String, content: T): Int {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return getContentLength(content) / 1024
            }
        }
    }

    open fun addContentToCache(key: String, content: T?) {
        if (getContentFromCache(key) == null && content != null ) memoryCache.put(key, content)
    }

    open fun getContentFromCache(key: String) : T? = memoryCache.get(key)

    abstract fun getContentLength(content: T): Int
}