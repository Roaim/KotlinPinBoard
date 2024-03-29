package com.roaim.pindownloader

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.stream.JsonReader
import com.roaim.pindownloader.core.CacheDataSource
import com.roaim.pindownloader.core.PinDownloader
import com.roaim.pindownloader.core.RemoteDataSource
import okhttp3.ResponseBody
import java.io.StringReader

object JsonCacheDataSource : CacheDataSource<JsonElement>() {
    @ExperimentalStdlibApi
    override fun getContentLength(content: JsonElement): Int {
        return Gson().toJson(content).run { encodeToByteArray().size }
    }
}

object JsonRemoteDataSource : RemoteDataSource<JsonElement>() {
    override suspend fun convert(response: ResponseBody?): JsonElement? =
        response?.string().let {
            val reader = JsonReader(StringReader(it))
            reader.isLenient = true
            Gson().fromJson(reader, JsonElement::class.java)
        }

}

open class JsonPinDownloader(cacheDataSource: CacheDataSource<JsonElement> = JsonCacheDataSource, remoteDataSource: RemoteDataSource<JsonElement> = JsonRemoteDataSource
) : PinDownloader<JsonElement>(cacheDataSource, remoteDataSource)

fun <T> JsonElement.toPoJo(c: Class<T>): T {
    return Gson().fromJson(this, c)
}
