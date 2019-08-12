package com.roaim.pindownloader.core

import com.roaim.pindownloader.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface RemoteApi {

    @Streaming
    @GET
    @Throws(Exception::class)
    suspend fun getContent(@Url url: String): ResponseBody //TODO replace ResponseBody with Response<ResponseBody> for better control

    companion object Factory {
        fun create(): RemoteApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
            return retrofit.create(RemoteApi::class.java)
        }
    }
}