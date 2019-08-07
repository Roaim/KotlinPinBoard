package com.roaim.pindownloader

import com.roaim.pindownloader.data.CacheDataSource
import com.roaim.pindownloader.data.RemoteDataSource
import io.reactivex.Observable

abstract class PinDownloader<T>(
    private val cacheDataSource: CacheDataSource<T>,
    private val remoteDataSource: RemoteDataSource<T>
) {

    fun download(url:String) : Observable<T>? {
        return cacheDataSource.getContentFromCache(url)?.toObservable() ?: remoteDataSource.getRemoteContent(url, cacheDataSource)
    }
}

private fun <T> T?.toObservable(): Observable<T>? {
    return Observable.just(this)
}
