package com.roaim.cache.disk

import com.roaim.cache.core.JournalStore
import java.io.BufferedWriter
import java.io.File

class DiskJournalStore(private val cacheDir: File) : JournalStore {
    companion object {
        const val FILE_NAME = "journal"
    }

//    constructor(cacheDir: String) : this(File(cacheDir))

    @Synchronized
    override fun count(): Int = cacheDir.takeIf { it.exists() }?.list { _, name ->
        FILE_NAME != name
    }?.size ?: 0

    @Synchronized
    override fun evict(): String = getJournalFile().run {
        val list = readLines()
        updateJournalFile(bufferedWriter(), list.takeLast(list.size.dec()))
        list[0]
    }

    @Synchronized
    override fun insert(element: String) {
        getJournalFile().apply {
            bufferedReader().use {
                val lineBreak = if (isNotEmpty()) "\n" else ""
                appendText("$lineBreak$element")
            }
        }
    }

    @Synchronized
    override fun prioritize(element: String) {
        getJournalFile().apply {
            val lines = readLines().toMutableList()
            if (lines.remove(element)) {
                lines.add(element)
                updateJournalFile(bufferedWriter(), lines)
            }
        }
    }

    private fun updateJournalFile(writer: BufferedWriter, list: List<String>) {
        writer.use {
            list.forEach { item ->
                it.write(item)
                if (item != list.last()) it.newLine()
            }
        }
    }

    private fun getJournalFile() = synchronized(this) {
        getFile(cacheDir, FILE_NAME).apply {
            if (exists().not()) {
                createNewFile()
            }
        }
    }
}