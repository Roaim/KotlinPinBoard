package com.roaim.pindownloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.roaim.pindownloader.data.CacheDataSource
import com.roaim.pindownloader.data.RemoteDataSource
import okhttp3.ResponseBody

object BitmapRemoteDataSource : RemoteDataSource<Bitmap>() {
    override fun convert(response: ResponseBody?): Bitmap? {
        // TODO replace bitmap decoding with the procedure showed in
        //  https://developer.android.com/topic/performance/graphics/load-bitmap
        // now skipping for not to write unit test
        return response?.bytes()?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
    }
}

object BitmapCacheDataSource : CacheDataSource<Bitmap>() {
    override fun getContentLength(content: Bitmap): Int = content.byteCount
}

open class BitmapPinDownloader(
    cacheDataSource: BitmapCacheDataSource = BitmapCacheDataSource,
    remoteDataSource: BitmapRemoteDataSource = BitmapRemoteDataSource
) : PinDownloader<Bitmap>(cacheDataSource, remoteDataSource)

