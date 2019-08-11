package com.roaim.pindownloader.data

import okhttp3.ResponseBody

abstract class RemoteDataSource<T>(
    val remoteApi: RemoteApi = RemoteApi.create()
) {

    suspend fun getRemoteContent(url: String): T? {
        val content: ResponseBody? = try {
            remoteApi.getContent(url)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return convert(content)
    }

    abstract fun convert(response: ResponseBody?): T?
}