package com.roaim.pindownloader

import com.roaim.pindownloader.mock.MockJson
import com.roaim.pindownloader.mock.MockPoJo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class JsonRemoteDataSourceTest {

    private val testDispatcher = TestCoroutineDispatcher()
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun convert() = testDispatcher.runBlockingTest {
        val mockResponseBody = mock(ResponseBody::class.java)
        val remoteDataSource = spy(JsonRemoteDataSource::class.java)

        `when`(mockResponseBody.string()).thenReturn(MockJson.string)

        val jeConverted = remoteDataSource.convert(mockResponseBody)

        assertNotNull(jeConverted)
        assertEquals(jeConverted?.toPoJo(MockPoJo::class.java).toString(), MockJson.poJo.toString())

    }
}