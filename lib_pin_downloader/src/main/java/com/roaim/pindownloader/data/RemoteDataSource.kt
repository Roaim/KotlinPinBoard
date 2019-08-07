package com.roaim.pindownloader.data

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response

abstract class RemoteDataSource<T>(
    val remoteApi: RemoteApi = RemoteApi.create()
) {

    fun getRemoteContent(url: String, cacheDataSource: CacheDataSource<T>) : Observable<T> {
        return remoteApi.getContent(url)
            .flatMap { response -> Observable.create { subscriber ->
                run {
                    val content = convert(response)
                    subscriber.onNext(content)
                    cacheDataSource.addContentToCache(url, content)
                    subscriber.onComplete()
                }
            }}
    }

    abstract fun convert(response: Response<ResponseBody>?): T?
}