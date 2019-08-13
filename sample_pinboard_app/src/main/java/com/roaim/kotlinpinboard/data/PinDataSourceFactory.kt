package com.roaim.kotlinpinboard.data

import androidx.paging.DataSource
import com.roaim.kotlinpinboard.data.model.Pin

class PinDataSourceFactory(private val pinDataSource: PinDataSource) : DataSource.Factory<String, Pin>() {
    override fun create(): DataSource<String, Pin> {
        return pinDataSource
    }
}