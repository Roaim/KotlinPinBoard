package com.roaim.pindownloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.roaim.pindownloader.core.CacheDataSource
import com.roaim.pindownloader.core.PinDownloader
import com.roaim.pindownloader.core.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

object BitmapRemoteDataSource : RemoteDataSource<Bitmap>() {
    override suspend fun convert(response: ResponseBody?): Bitmap? = withContext(Dispatchers.IO){
        // TODO replace bitmap decoding with the procedure showed in
        //  https://developer.android.com/topic/performance/graphics/load-bitmap
        // now skipping and letting OutOfMemoryException for not to write unit test
        response?.bytes()?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
    }
}

object BitmapCacheDataSource : CacheDataSource<Bitmap>() {
    override fun getContentLength(content: Bitmap): Int = content.byteCount
}

open class BitmapPinDownloader(
    cacheDataSource: CacheDataSource<Bitmap> = BitmapCacheDataSource,
    remoteDataSource: RemoteDataSource<Bitmap> = BitmapRemoteDataSource
) : PinDownloader<Bitmap>(cacheDataSource, remoteDataSource)

