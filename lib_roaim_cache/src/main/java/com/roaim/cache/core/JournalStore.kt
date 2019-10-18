package com.roaim.cache.core

interface JournalStore {
    fun count(): Int
    fun isNotEmpty() = count() > 0
    fun evict(): String
    fun insert(element: String)
    fun prioritize(element: String)
}