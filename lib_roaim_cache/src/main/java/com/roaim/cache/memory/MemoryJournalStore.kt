package com.roaim.cache.memory

import com.roaim.cache.core.JournalStore
import java.util.*

class MemoryJournalStore : JournalStore {

    private val mJournal: Queue<String> = LinkedList<String>()

    override fun count(): Int = mJournal.count()

    override fun evict(): String = mJournal.poll()

    override fun insert(element: String) {
        mJournal.offer(element)
    }

    override fun prioritize(element: String) {
        if (mJournal.remove(element)) {
            mJournal.offer(element)
        }
    }
}