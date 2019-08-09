package com.roaim.pindownloader

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.roaim.pindownloader.data.CacheDataSource
import com.roaim.pindownloader.data.RemoteDataSource

open class PinDownloader<T>(
    private val cacheDataSource: CacheDataSource<T>,
    private val remoteDataSource: RemoteDataSource<T>
) {
    fun download(url: String): LiveData<T> = liveData {
        emit(cacheDataSource.getContentFromCache(url) ?: remoteDataSource.getRemoteContent(url, cacheDataSource))
    }
}

