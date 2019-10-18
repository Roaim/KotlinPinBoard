package com.roaim.cache.core

abstract class AbstractCache<T>(
    private val capacity: Int,
    private val cacheStore: CacheStore<T>,
    private val journalStore: JournalStore
) : Cache<T> {
    private var mJournalSize = cacheStore.size()

    override fun put(key: String, item: T) {
        mJournalSize = mJournalSize.plus(getItemSize(item)).apply {
            takeIf { it > capacity && journalStore.isNotEmpty() }?.apply {
                journalStore.evict().also {
                    cacheStore.delete(it)?.apply { onItemRemoved(it, this, key, item) }
                }
            }
        }
        journalStore.insert(key)
        cacheStore.write(key, item)
    }

    override fun get(key: String) = cacheStore.read(key)?.also {
        journalStore.prioritize(key)
        println("${javaClass.simpleName} : $key")
    }

    override fun count() = journalStore.count()

    override fun onItemRemoved(oldKey: String, oldItem: T, newKey: String, newItem: T) {}
}