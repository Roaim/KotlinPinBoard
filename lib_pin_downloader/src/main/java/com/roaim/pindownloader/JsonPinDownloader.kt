package com.roaim.pindownloader

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.stream.JsonReader
import com.roaim.cache.CacheConfig
import com.roaim.cache.RoaimCache
import com.roaim.pindownloader.core.CacheDataSource
import com.roaim.pindownloader.core.PinDownloader
import com.roaim.pindownloader.core.RemoteDataSource
import okhttp3.ResponseBody
import java.io.StringReader

@ExperimentalStdlibApi
open class JsonCacheDataSource(cacheDir: String) : CacheDataSource<JsonElement>(RoaimCache(
    CacheConfig.Builder()
        .setCacheDir(cacheDir)
        .build(),
    {
        Gson().toJson(it).run { encodeToByteArray() }
    },
    {
        Gson().fromJson(String(it), JsonElement::class.java)
    }
))

object JsonRemoteDataSource : RemoteDataSource<JsonElement>() {
    override suspend fun convert(response: ResponseBody?): JsonElement? =
        response?.string()?.let {
            val reader = JsonReader(StringReader(it))
            reader.isLenient = true
            Gson().fromJson(reader, JsonElement::class.java)
        }

}

@ExperimentalStdlibApi
open class JsonPinDownloader(
    cacheDataSource: CacheDataSource<JsonElement>,
    remoteDataSource: RemoteDataSource<JsonElement> = JsonRemoteDataSource
) : PinDownloader<JsonElement>(cacheDataSource, remoteDataSource)

fun <T> JsonElement.toPoJo(c: Class<T>): T {
    return Gson().fromJson(this, c)
}
