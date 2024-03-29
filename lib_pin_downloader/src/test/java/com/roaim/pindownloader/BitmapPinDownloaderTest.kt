package com.roaim.pindownloader

import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class BitmapPinDownloaderTest {
    /**
     * In this test, LiveData will immediately post values without switching threads.
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private val url = "this://is.a.url"
    private lateinit var mockRemote: BitmapRemoteDataSource
    private lateinit var mockCache: BitmapCacheDataSource
    private lateinit var bitmapPinDownloader: BitmapPinDownloader
    private lateinit var mockCacheBmp: Bitmap
    private lateinit var mockRemoteBmp: Bitmap

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        //TODO Figure out why mockito annotation is not working
        mockRemote = mock(BitmapRemoteDataSource::class.java)
        mockCache = mock(BitmapCacheDataSource::class.java)
        bitmapPinDownloader = BitmapPinDownloader(mockCache, mockRemote)
        mockCacheBmp = mock(Bitmap::class.java)
        mockRemoteBmp = mock(Bitmap::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun download_whenNotInCache_fetchFromRemote() = testScope.runBlockingTest {
        `when`(mockCache.getContentFromCache(url)).thenReturn(null)
        `when`(mockRemote.getRemoteContent(url)).thenReturn(mockRemoteBmp)

        val download = bitmapPinDownloader.download(url)

        download.observeForTesting {
            assertEquals(download.value, mockRemoteBmp)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun download_whenFetchFromRemote__shouldAddToCache() = testScope.runBlockingTest {
        `when`(mockCache.getContentFromCache(url)).thenReturn(null)
        `when`(mockRemote.getRemoteContent(url)).thenReturn(mockRemoteBmp)

        bitmapPinDownloader.download(url).apply {
            observeForTesting {
                verify(mockCache).addContentToCache(url, value)
            }
        }
    }

    @Test
    fun download_whenInCache_retrieveFromCache() = testScope.runBlockingTest {
        `when`(mockCache.getContentFromCache(url)).thenReturn(mockCacheBmp)
        `when`(mockRemote.getRemoteContent(url)).thenReturn(mockRemoteBmp)

        val download = bitmapPinDownloader.download(url)

        download.observeForTesting {
            assertEquals(download.value, mockCacheBmp)
        }
    }
}

// helper method to allow us to get the value from a LiveData
// LiveData won't publish a result until there is at least one observer
fun <T> LiveData<T>.observeForTesting(
    block: () -> Unit
) {
    val observer = Observer<T> { Unit }
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}