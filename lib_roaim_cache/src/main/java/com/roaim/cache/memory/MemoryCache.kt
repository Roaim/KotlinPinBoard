package com.roaim.cache.memory

import com.roaim.cache.core.AbstractCache

open class MemoryCache<T>(
    capacity: Int,
    private val itemSize: ((T) -> Int)? = null,
    private val evicted: ((String, T) -> Unit)? = null
) : AbstractCache<T>(capacity, MemoryCacheStore<T>(itemSize), MemoryJournalStore()) {

    override fun getItemSize(item: T): Int = itemSize?.invoke(item) ?: 1

    override fun onItemRemoved(oldKey: String, oldItem: T, newKey: String, newItem: T) {
        evicted?.invoke(oldKey, oldItem)
    }

}