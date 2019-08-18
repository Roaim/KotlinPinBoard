package com.roaim.kotlinpinboard.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.roaim.kotlinpinboard.data.model.LoremPicksum

class PinRepository(private val pinDataSource: PinDataSource = PinDataSource()) {
    private val dataSourceFactory = PinDataSourceFactory(pinDataSource)

    fun getBitmap(url: String) = pinDataSource.getBitmap(url)

    fun getPins(): LiveData<PagedList<LoremPicksum>> {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPrefetchDistance(10)
            .build()
        return LivePagedListBuilder(dataSourceFactory, config).build()
    }
}