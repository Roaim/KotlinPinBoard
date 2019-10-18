package com.roaim.cache.core

interface CacheStore<T> {
    fun read(key: String): T?
    fun write(key: String, item: T)
    fun delete(key: String): T?
    fun size(): Int
}