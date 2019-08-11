package com.roaim.pindownloader

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.roaim.pindownloader.data.CacheDataSource
import com.roaim.pindownloader.data.RemoteDataSource

open class PinDownloader<T>(
    private val cacheDataSource: CacheDataSource<T>,
    private val remoteDataSource: RemoteDataSource<T>
) {
    fun download(url: String): LiveData<T?> = liveData {
        emit(cacheDataSource.getContentFromCache(url) ?: remoteDataSource.getRemoteContent(url)?.also {
            cacheDataSource.addContentToCache(url, it)
        })
    }
}

// TODO replace above code with following and also make remoteSource return Pin<T>

/*
open class PinDownloader<T>(
    private val cacheDataSource: CacheDataSource<T>,
    private val remoteDataSource: RemoteDataSource<T>
) {
    fun download(url: String): LiveData<Pin<T>> = liveData {
        emit(Loading(true))
        val value = cacheDataSource.getContentFromCache(url) ?: remoteDataSource.getRemoteContent(url, cacheDataSource)
        emit(value?.let { Success(it) } ?: Failed("Failed to download"))
        emit(Loading(false))
    }
}

sealed class Pin<out T>
data class Loading(val progress : Boolean): Pin<Nothing>()
data class Success<T>(val data: T) : Pin<T>()
data class Failed(val errorMessage : String) : Pin<Nothing>()
*/
