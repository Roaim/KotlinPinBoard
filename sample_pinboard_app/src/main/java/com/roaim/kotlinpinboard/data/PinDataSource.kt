package com.roaim.kotlinpinboard.data

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.paging.PageKeyedDataSource
import com.roaim.kotlinpinboard.BuildConfig
import com.roaim.kotlinpinboard.data.model.LoremPicksum
import com.roaim.kotlinpinboard.utils.Constants
import com.roaim.kotlinpinboard.utils.observeOnceInMain
import com.roaim.pindownloader.*

@ExperimentalStdlibApi
class PinDataSource(
    private val jsonPinDownloader: JsonPinDownloader = JsonPinDownloader(JsonCacheDataSource("${Environment.getDataDirectory()}/data/${BuildConfig.APPLICATION_ID}/files/cache/json")),
    private val bitmapPinDownloader: BitmapPinDownloader = BitmapPinDownloader(
        BitmapCacheDataSource(
            "${Environment.getDataDirectory()}/data/${BuildConfig.APPLICATION_ID}/files/cache/bmp"
        )
    )
) : PageKeyedDataSource<Int, LoremPicksum>() {
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, LoremPicksum>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, LoremPicksum>) {
        getPin(Constants.getImageListUrl(params.key, params.requestedLoadSize)).apply {
            observeOnceInMain(Observer {
                it?.also { callback.onResult(it, params.key.inc()) }
            })
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, LoremPicksum>) {
        getPin(Constants.getImageListUrl()).apply {
            observeOnceInMain(Observer {
                it?.also { callback.onResult(it, 0, 2) }
            })
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