package com.roaim.pindownloader.core

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
        val convert = convert(content)
        return convert
    }

    abstract suspend fun convert(response: ResponseBody?): T?
}