package com.roaim.pindownloader.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
        return withContext(Dispatchers.IO) {convert(content)}
    }

    abstract suspend fun convert(response: ResponseBody?): T?
}