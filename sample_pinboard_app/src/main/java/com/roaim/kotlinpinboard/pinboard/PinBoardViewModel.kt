package com.roaim.kotlinpinboard.pinboard

import android.view.View
import androidx.lifecycle.ViewModel
import com.roaim.kotlinpinboard.data.PinRepository
import com.roaim.kotlinpinboard.data.model.LoremPicksum

class PinBoardViewModel(private val repository: PinRepository = PinRepository()) : ViewModel() {

    fun downloadThumb(pin: LoremPicksum) = pin.id?.let { id ->
        repository.getBitmap("https://picsum.photos/id/$id/240/320").apply {
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
