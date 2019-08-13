package com.roaim.kotlinpinboard.pinboard

import android.view.View
import androidx.lifecycle.ViewModel
import com.roaim.kotlinpinboard.data.PinRepository
import com.roaim.kotlinpinboard.data.model.Pin

class PinBoardViewModel(private val repository: PinRepository = PinRepository()) : ViewModel() {

    fun downloadThumb(pin: Pin) = pin.urls?.thumb?.let { thumbUrl ->
        repository.getBitmap(thumbUrl).apply {
            pin.progress.value = View.VISIBLE
            observeForever {
                pin.progress.value = View.GONE
                pin.thumb.value = it
            }
            removeObserver { }
        }
    }

    val pinList = repository.getPins()

}
