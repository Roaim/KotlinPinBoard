package com.roaim.pindownloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.roaim.cache.CacheConfig
import com.roaim.cache.RoaimCache
import com.roaim.pindownloader.core.CacheDataSource
import com.roaim.pindownloader.core.PinDownloader
import com.roaim.pindownloader.core.RemoteDataSource
import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream

object BitmapRemoteDataSource : RemoteDataSource<Bitmap>() {
    override suspend fun convert(response: ResponseBody?): Bitmap? =
        // TODO replace bitmap decoding with the procedure showed in
        //  https://developer.android.com/topic/performance/graphics/load-bitmap
        // now skipping and letting OutOfMemoryException for not to write unit test
        response?.bytes()?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }

}

open class BitmapCacheDataSource(cacheDir: String) : CacheDataSource<Bitmap>(RoaimCache(
    CacheConfig.Builder()
        .setCacheDir(cacheDir)
        .build(),
    {
        val stream = ByteArrayOutputStream()
        it.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.toByteArray()
    },
    {
        BitmapFactory.decodeByteArray(it, 0, it.size)
    }
))

open class BitmapPinDownloader(
    cacheDataSource: CacheDataSource<Bitmap>,
    remoteDataSource: RemoteDataSource<Bitmap> = BitmapRemoteDataSource
) : PinDownloader<Bitmap>(cacheDataSource, remoteDataSource)

