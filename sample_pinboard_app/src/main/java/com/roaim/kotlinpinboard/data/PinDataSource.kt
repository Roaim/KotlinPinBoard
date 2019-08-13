package com.roaim.kotlinpinboard.data

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.ItemKeyedDataSource
import com.roaim.kotlinpinboard.BuildConfig
import com.roaim.kotlinpinboard.data.model.Pin
import com.roaim.pindownloader.BitmapPinDownloader
import com.roaim.pindownloader.JsonPinDownloader
import com.roaim.pindownloader.toPoJo

class PinDataSource(
    private val jsonPinDownloader: JsonPinDownloader = JsonPinDownloader(),
    private val bitmapPinDownloader: BitmapPinDownloader = BitmapPinDownloader()
) : ItemKeyedDataSource<String, Pin>() {
    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Pin>) {
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Pin>) {
        getPin(params.key).apply {
            Handler(Looper.getMainLooper()).post {
                observeForever {
                    callback.onResult(it)
                    removeObserver {}
                }
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Pin>) {
        getPin(BuildConfig.SAMPLE_JSON_URL).apply {
            Handler(Looper.getMainLooper()).post {
                observeForever {
                    callback.onResult(it)
                    removeObserver {}
                }
            }
        }
    }

    override fun getKey(item: Pin): String = BuildConfig.SAMPLE_JSON_URL

    private fun getPin(url: String) = Transformations.switchMap(
        jsonPinDownloader.download(url)
    ) {
        it?.toPoJo(Array<Pin>::class.java)?.run {
            MutableLiveData<List<Pin>>(asList())
        }
    }

    fun getBitmap(url: String) = bitmapPinDownloader.download(url)
}