package com.roaim.pindownloader.data

import okhttp3.ResponseBody

abstract class RemoteDataSource<T>(
    val remoteApi: RemoteApi = RemoteApi.create()
) {

    suspend fun getRemoteContent(url: String, cacheDataSource: CacheDataSource<T>): T {
        val content = remoteApi.getContent(url)
        val convertedContent = convert(content)
        cacheDataSource.addContentToCache(url, convertedContent)
        return convertedContent
    }

    abstract fun convert(response: ResponseBody): T
}