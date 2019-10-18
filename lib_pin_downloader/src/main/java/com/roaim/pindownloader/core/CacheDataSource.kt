package com.roaim.pindownloader.core

import com.roaim.cache.RoaimCache

open class CacheDataSource<T>(private val cache: RoaimCache<T>) {

    open fun addContentToCache(key: String, content: T) = cache.addToCache(key, content)

    open fun getContentFromCache(key: String): T? = cache.getFromCache(key)
}