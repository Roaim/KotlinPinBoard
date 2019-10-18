package com.roaim.kotlinpinboard.utils

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.roaim.kotlinpinboard.data.model.LoremPicksum
import com.roaim.kotlinpinboard.pinboard.PinAdapter

@UseExperimental(ExperimentalStdlibApi::class)
@BindingAdapter("pinItems")
fun setItems(listView: RecyclerView, items: LiveData<PagedList<LoremPicksum>>) {
    listView.let { it.adapter as PinAdapter }.run {
        submitList(items.value)
    }
}

@BindingAdapter("imageBitmap")
fun setImageBitmap(imageView: ImageView, liveData: LiveData<Bitmap?>) {
    liveData.value?.let { imageView.setImageBitmap(it) }
}
