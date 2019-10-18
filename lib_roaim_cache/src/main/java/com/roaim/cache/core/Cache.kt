package com.roaim.cache.core

import com.roaim.cache.disk.DiskCache
import com.roaim.cache.memory.MemoryCache

interface Cache<T> {
    fun put(key: String, item: T)
    fun get(key: String): T?
    fun count(): Int
    fun getItemSize(item: T): Int
    fun onItemRemoved(oldKey: String, oldItem: T, newKey: String, newItem: T)

    companion object Factory {
        fun <T> createMemoryCache(
            capacity: Int,
            itemSize: ((T) -> Int)? = null,
            evicted: ((String, T) -> Unit)? = null
        ): Cache<T> = MemoryCache(capacity, itemSize, evicted)

        fun <T> createDiskCache(
            capacity: Int,
            cacheDir: String,
            itemToBytes: (T) -> ByteArray,
            bytesToItem: (ByteArray) -> T,
            evicted: ((String, T) -> Unit)? = null
        ): Cache<T> = DiskCache(capacity, cacheDir, itemToBytes, bytesToItem, evicted)
    }
}