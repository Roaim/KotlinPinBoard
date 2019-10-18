package com.roaim.pindownloader

import android.graphics.Bitmap
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy

class BitmapCacheDataSourceTest {

    private lateinit var bitmap: Bitmap
    private lateinit var bitmapCacheDataSource: BitmapCacheDataSource

    @Before
    fun setUp() {
        //TODO Figure out why mockito annotation is not working
        bitmap = mock(Bitmap::class.java)
        bitmapCacheDataSource = spy(BitmapCacheDataSource::class.java)
    }

    /*@Test
    fun getContentLength() {
        val size = bitmapCacheDataSource.cacheSize * 1024 / 10
        `when`(bitmap.byteCount).thenReturn(size)
        bitmapCacheDataSource.addContentToCache("abc", bitmap)
        verify(bitmapCacheDataSource).getContentLength(bitmap)
        assertEquals(bitmapCacheDataSource.getContentLength(bitmap), size)
    }*/

    /*@Test
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
    }*/

    /*@Test
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
    }*/

}