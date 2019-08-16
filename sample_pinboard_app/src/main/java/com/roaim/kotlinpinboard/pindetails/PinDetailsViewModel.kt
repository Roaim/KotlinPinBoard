package com.roaim.kotlinpinboard.pindetails

import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.roaim.kotlinpinboard.data.PinRepository
import com.roaim.kotlinpinboard.utils.observeOnce

class PinDetailsViewModel(private val repository: PinRepository) : ViewModel() {
    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int>
        get() = _progress

    init {
        _progress.value = View.GONE
    }

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    fun setTitle(title: String) = with(title) { _title.value = this }

    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap>
        get() = _image

    fun downloadImage(url: String) = repository.getBitmap(url).apply {
        _progress.value = View.VISIBLE
        observeOnce(Observer {
            _progress.value = View.GONE
            _image.value = it
        })
    }
}
