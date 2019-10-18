package com.roaim.cache.disk

import com.roaim.cache.core.AbstractCache
import java.io.File

open class DiskCache<T>(
    capacity: Int,
    cacheDir: File,
    private val itemBytes: (T) -> ByteArray,
    byteItem: (ByteArray) -> T,
    private val evicted: ((String, T) -> Unit)? = null
) : AbstractCache<T>(
    capacity,
    DiskCacheStore<T>(cacheDir, itemBytes, byteItem),
    DiskJournalStore(cacheDir)
) {

    constructor(
        capacity: Int,
        cacheDir: String,
        itemBytes: (T) -> ByteArray,
        byteItem: (ByteArray) -> T,
        evicted: ((String, T) -> Unit)? = null
    )
            : this(capacity, File(cacheDir), itemBytes, byteItem, evicted)

    @Synchronized
    override fun getItemSize(item: T): Int = itemBytes.invoke(item).size

    @Synchronized
    override fun onItemRemoved(oldKey: String, oldItem: T, newKey: String, newItem: T) {
        evicted?.invoke(oldKey, oldItem)
    }

}

fun getFile(cacheDir: File, fileName: String) = File(
    cacheDir.apply {
        takeIf { exists().not() }?.mkdirs()
    },
    fileName
)