package com.roaim.cache.disk

import com.roaim.cache.core.CacheStore
import java.io.File

class DiskCacheStore<T>(
    private val cacheDir: File,
    private val itemToBytes: (T) -> ByteArray,
    private val byteToItem: (ByteArray) -> T
) : CacheStore<T> {

//    constructor(cacheDir: String, itemToBytes: (T) -> ByteArray, byteToItem: (ByteArray) -> T) : this(File(cacheDir), itemToBytes, byteToItem)

    @Synchronized
    override fun read(key: String): T? = getCacheFile(key).takeIf {
        it.exists()
    }?.run {
        byteToItem(
            readBytes()
        )
    }

    @Synchronized
    override fun write(key: String, item: T) {
        getCacheFile(key).writeBytes(itemToBytes(item))
    }

    @Synchronized
    override fun delete(key: String): T? = read(key).also { getCacheFile(key).delete() }

    private fun getCacheFile(key: String) = synchronized(this) {
        getFile(cacheDir, key)
    }

    @Synchronized
    override fun size(): Int =
        cacheDir.takeIf { it.exists() }?.listFiles { _, name -> DiskJournalStore.FILE_NAME != name }?.sumBy {
            it.length().toInt()
        } ?: 0
}