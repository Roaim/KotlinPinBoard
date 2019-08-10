package com.roaim.pindownloader

import android.graphics.Bitmap
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class BitmapCacheDataSourceTest {

    lateinit var bitmap: Bitmap

    lateinit var bitmapCacheDataSource: BitmapCacheDataSource

    @Before
    fun setUp() {
        bitmap = mock(Bitmap::class.java)
        bitmapCacheDataSource = spy(BitmapCacheDataSource::class.java)
    }

    @Test
    fun getContentLength() {
        val size = bitmapCacheDataSource.cacheSize * 1024 / 10
        `when`(bitmap.byteCount).thenReturn(size)
        bitmapCacheDataSource.addContentToCache("abc", bitmap)
        verify(bitmapCacheDataSource).getContentLength(bitmap)
        assertEquals(bitmapCacheDataSource.getContentLength(bitmap), size)
    }

    @Test
    fun whenReachesCacheLimit_shouldEvictLessPriorityItem() {
        val size = bitmapCacheDataSource.cacheSize * 1024 / 5
        lateinit var firstBmp: Bitmap
        lateinit var firstKey: String
        repeat(10) {
            val bmp = mock(Bitmap::class.java)
            `when`(bmp.byteCount).thenReturn(size.plus(it))
            val key = "key$it"
            if (it == 1) {
                firstBmp = bmp
                firstKey = key
            }
            bitmapCacheDataSource.addContentToCache(key, bmp)
        }
        assertNotEquals(bitmapCacheDataSource.getContentFromCache(firstKey), firstBmp)
    }

    @Test
    fun withinCacheLimit_shouldRetainFromCache() {
        val size = bitmapCacheDataSource.cacheSize * 1024 / 5
        lateinit var firstBmp: Bitmap
        lateinit var firstKey: String
        repeat(3) {
            val bmp = mock(Bitmap::class.java)
            `when`(bmp.byteCount).thenReturn(size.plus(it))
            val key = "key$it"
            if (it == 1) {
                firstBmp = bmp
                firstKey = key
            }
            bitmapCacheDataSource.addContentToCache(key, bmp)
        }
        assertEquals(bitmapCacheDataSource.getContentFromCache(firstKey), firstBmp)
    }

}