package com.roaim.kotlinpinboard.data

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import com.roaim.kotlinpinboard.BuildConfig
import com.roaim.kotlinpinboard.data.model.LoremPicksum
import com.roaim.pindownloader.BitmapPinDownloader
import com.roaim.pindownloader.JsonPinDownloader
import com.roaim.pindownloader.toPoJo

class PinDataSource(
    private val jsonPinDownloader: JsonPinDownloader = JsonPinDownloader(),
    private val bitmapPinDownloader: BitmapPinDownloader = BitmapPinDownloader()
) : PageKeyedDataSource<Int, LoremPicksum>() {
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, LoremPicksum>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, LoremPicksum>) {
        getPin("https://picsum.photos/v2/list?page=${params.key}&limit=${params.requestedLoadSize}").apply {
            Handler(Looper.getMainLooper()).post {
                observeForever {
                    callback.onResult(it, params.key.inc())
                    removeObserver {}
                }
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, LoremPicksum>) {
        getPin("https://picsum.photos/v2/list?page=1&limit=10").apply {
            Handler(Looper.getMainLooper()).post {
                observeForever {
                    callback.onResult(it, 0, 2)
                    removeObserver {}
                }
            }
        }
    }

    private fun getPin(url: String) = Transformations.switchMap(
        jsonPinDownloader.download(url)
    ) {
        it?.toPoJo(Array<LoremPicksum>::class.java)?.run {
            MutableLiveData<List<LoremPicksum>>(asList())
        }
    }

    fun getBitmap(url: String) = bitmapPinDownloader.download(url)
}