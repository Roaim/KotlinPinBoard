package com.roaim.pindownloader

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.JsonElement
import com.roaim.pindownloader.core.CacheDataSource
import com.roaim.pindownloader.core.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class JsonPinDownloaderTest {
    /**
     * In this test, LiveData will immediately post values without switching threads.
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val url = BuildConfig.BASE_URL
    private lateinit var mockRemote: RemoteDataSource<JsonElement>
    private lateinit var mockCache: CacheDataSource<JsonElement>
    private lateinit var jsonDownloader: JsonPinDownloader

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockRemote = mock(JsonRemoteDataSource::class.java)
        mockCache = spy(JsonCacheDataSource::class.java)
        jsonDownloader = JsonPinDownloader(mockCache, mockRemote)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun download_fromRemote_addToCache_getContentLength() = testDispatcher.runBlockingTest {
        val mockJsonElement = spy(JsonElement::class.java)
        `when`(mockCache.getContentFromCache(url)).thenReturn(null)
        `when`(mockRemote.getRemoteContent(url)).thenReturn(mockJsonElement)
        jsonDownloader.download(url).observeForTesting {
            verify(mockCache).addContentToCache(url, mockJsonElement)
            verify(mockCache).getContentLength(mockJsonElement)
        }
    }

}