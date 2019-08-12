package com.roaim.pindownloader

import com.roaim.pindownloader.mock.MockJson
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.spy

class JsonCacheDataSourceTest {

    @ExperimentalStdlibApi
    @Test
    fun getContentLength() {
        val source = MockJson.string
        val jsonElement = MockJson.jsonElement
        val cacheDataSource = spy(JsonCacheDataSource::class.java)
        val jeLength = cacheDataSource.getContentLength(jsonElement)
        println("Source byte = ${source.encodeToByteArray().size} and JsonElement Len = $jeLength")
        assertTrue(jeLength > 0)
    }
}