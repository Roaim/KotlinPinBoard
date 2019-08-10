package com.roaim.pindownloader

import android.graphics.Bitmap
import com.roaim.pindownloader.data.RemoteApi
import com.roaim.pindownloader.mock.MockBitmapRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class BitmapRemoteDataSourceTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(testDispatcher)

    lateinit var mockResponseBody: ResponseBody
    lateinit var mockApi: RemoteApi
    lateinit var mockCacheDataSource: BitmapCacheDataSource
    lateinit var bitmapRemoteDataSource: MockBitmapRemoteDataSource
    lateinit var mockBitmap: Bitmap

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockResponseBody = spy(ResponseBody::class.java)
        mockApi = spy(RemoteApi::class.java)
        mockCacheDataSource = mock(BitmapCacheDataSource::class.java)
        bitmapRemoteDataSource = spy(MockBitmapRemoteDataSource::class.java)
        mockBitmap = spy(Bitmap::class.java)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getRemoteContent_shouldReturnBitmap() {

    }

    @ExperimentalCoroutinesApi
    @Test
    fun getRemoteContent_shouldAddToCache() = testScope.runBlockingTest {
        val url = "www.bitmap.com"
        `when`(bitmapRemoteDataSource.remoteApi.getContent(url)).thenReturn(mockResponseBody)

        val remoteContent = bitmapRemoteDataSource.getRemoteContent(url, mockCacheDataSource)

        verify(mockCacheDataSource).addContentToCache(url, remoteContent)
    }


    @Test
    fun getRemoteApi() {
        `when`(bitmapRemoteDataSource.remoteApi).thenReturn(mockApi)
        assertEquals(bitmapRemoteDataSource.remoteApi, mockApi)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun convert() = testScope.runBlockingTest {
        val url = "https://images.unsplash.com"
        `when`(bitmapRemoteDataSource.remoteApi.getContent(url)).thenReturn(mockResponseBody)

        bitmapRemoteDataSource.getRemoteContent(url, mockCacheDataSource)

        verify(bitmapRemoteDataSource).convert(mockResponseBody)
    }
}
