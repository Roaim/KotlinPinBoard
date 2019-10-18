package com.roaim.cache

import android.util.Base64
import com.roaim.cache.core.Cache

class RoaimCache<T>(
    config: CacheConfig = CacheConfig(),
    itemBytes: (T) -> ByteArray,
    bytesItem: (ByteArray) -> T
) {
    private val mCache = Cache.createMemoryCache(config.memorySize, {
        itemBytes(it).size
    }, this::onRemoved)
    private val dCache =
        Cache.createDiskCache(config.diskSize, config.cacheDir, itemBytes, bytesItem, { k, _ ->
            println("$k evicted from disk")
        })

    private fun onRemoved(key: String, value: T) {
        dCache.put(key, value)
        println("$key is added to disk")
    }

    fun addToCache(key: String, value: T) =
        mCache.put(key.toBase64(), value).also { println("${key.toBase64()} is added to memory") }

    fun getFromCache(key: String): T? = mCache.get(key.toBase64()) ?: dCache.get(key.toBase64())
}

data class CacheConfig(
    var memorySize: Int = (Runtime.getRuntime().maxMemory()).div(16).toInt(),
    var diskSize: Int = (Runtime.getRuntime().maxMemory()).times(2).toInt(),
    var cacheDir: String = "temp"
) {
    class Builder(private val cacheConfig: CacheConfig = CacheConfig()) {
        fun setMemorySize(memorySize: Int) = apply { cacheConfig.memorySize = memorySize }
        fun setDiskSize(diskSize: Int) = apply { cacheConfig.diskSize = diskSize }
        fun setCacheDir(cacheDir: String) = apply { cacheConfig.cacheDir = cacheDir }
        fun build() = cacheConfig
    }
}

fun String.toBase64(): String = replace("/", "#")
fun String.fromBase64(): String = String(Base64.decode(this, Base64.DEFAULT)).trim()

