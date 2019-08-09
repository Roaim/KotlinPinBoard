package com.roaim.pindownloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.roaim.pindownloader.data.CacheDataSource
import com.roaim.pindownloader.data.RemoteDataSource
import okhttp3.ResponseBody

object BitmapRemoteDataSource : RemoteDataSource<Bitmap>() {
    override fun convert(response: ResponseBody): Bitmap {
        val bytes = response.bytes()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}

object BitmapCacheDataSource : CacheDataSource<Bitmap>() {
    override fun getContentLength(content: Bitmap): Int = content.byteCount
}

open class BitmapPinDownloader : PinDownloader<Bitmap>(BitmapCacheDataSource, BitmapRemoteDataSource)

