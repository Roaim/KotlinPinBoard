package com.roaim.cache.memory

import com.roaim.cache.core.CacheStore

class MemoryCacheStore<T>(private val itemSize: ((T) -> Int)?) : CacheStore<T> {

    private val mStore = mutableMapOf<String, T>()

    override fun read(key: String): T? = mStore[key]

    override fun write(key: String, item: T) {
        mStore[key] = item
    }

    override fun delete(key: String): T? = mStore.remove(key)

    override fun size(): Int = mStore.values.sumBy { itemSize?.invoke(it) ?: 1 }
}