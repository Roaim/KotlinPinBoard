package com.roaim.kotlinpinboard.utils

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.roaim.kotlinpinboard.data.model.LoremPicksum
import com.roaim.kotlinpinboard.pinboard.PinAdapter

@BindingAdapter("pageItems")
fun setItems(listView: RecyclerView, items: LiveData<PagedList<LoremPicksum>>) {
    items.value.also {
        (listView.adapter as PinAdapter).submitList(it)
    }
}

@BindingAdapter("imageBitmap")
fun setImageBitmap(imageView: ImageView, liveData: LiveData<Bitmap>) {
    liveData.value?.let { imageView.setImageBitmap(it) }
}
