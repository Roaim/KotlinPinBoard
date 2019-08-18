package com.roaim.kotlinpinboard.data

import androidx.paging.DataSource
import com.roaim.kotlinpinboard.data.model.LoremPicksum

class PinDataSourceFactory(private val pinDataSource: PinDataSource) : DataSource.Factory<Int, LoremPicksum>() {
    override fun create(): DataSource<Int, LoremPicksum> {
        return pinDataSource
    }
}