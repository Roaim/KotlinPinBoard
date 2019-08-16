package com.roaim.kotlinpinboard.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.paging.PageKeyedDataSource
import com.roaim.kotlinpinboard.data.model.LoremPicksum
import com.roaim.kotlinpinboard.utils.Constants
import com.roaim.kotlinpinboard.utils.observeOnceInMain
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