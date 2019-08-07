package com.roaim.pindownloader.data

import com.roaim.pindownloader.BuildConfig
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

open interface RemoteApi {

    @Streaming
    @GET
    fun getContent(@Url url: String) : Observable<Response<ResponseBody>>

    companion object Factory {
        fun create(): RemoteApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()

            return retrofit.create(RemoteApi::class.java)
        }
    }
}