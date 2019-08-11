package com.roaim.pindownloader

import android.graphics.Bitmap
import com.roaim.pindownloader.data.RemoteApi
import com.roaim.pindownloader.mock.MockBitmapRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class BitmapRemoteDataSourceTest {
    private val url = "https://images.unsplash.com"

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var mockResponseBody: ResponseBody
    private lateinit var mockApi: RemoteApi
    private lateinit var mockCacheDataSource: BitmapCacheDataSource
    private lateinit var bitmapRemoteDataSource: MockBitmapRemoteDataSource
    private lateinit var mockBitmap: Bitmap

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        //TODO Figure out why mockito annotation is not working
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

    @ExperimentalCoroutinesApi
    @Test
    fun getRemoteContent_whenValidResponseBody_shouldReturnBitmap() = testScope.runBlockingTest {
        `when`(bitmapRemoteDataSource.remoteApi.getContent(url)).thenReturn(mockResponseBody)
        assertThat(bitmapRemoteDataSource.getRemoteContent(url, mockCacheDataSource), instanceOf(Bitmap::class.java))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getRemoteContent_whenApiException_shouldReturnNull() = testScope.runBlockingTest {
        `when`(bitmapRemoteDataSource.remoteApi.getContent(url)).thenThrow(Exception("Bang!"))
        assertNull(bitmapRemoteDataSource.getRemoteContent(url, mockCacheDataSource))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getRemoteContent_shouldAddToCache() = testScope.runBlockingTest {
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
        `when`(bitmapRemoteDataSource.remoteApi.getContent(url)).thenReturn(mockResponseBody)

        bitmapRemoteDataSource.getRemoteContent(url, mockCacheDataSource)

        verify(bitmapRemoteDataSource).convert(mockResponseBody)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun convert_nullResponseBody_nullBitmap() {
        val bmpRemoteDataSource = spy(BitmapRemoteDataSource::class.java)
        assertNull(bmpRemoteDataSource.convert(null))
    }
}
