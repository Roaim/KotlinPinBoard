package com.roaim.kotlinpinboard.pinboard

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roaim.kotlinpinboard.data.PinRepository
import com.roaim.kotlinpinboard.data.model.LoremPicksum
import com.roaim.kotlinpinboard.utils.Constants

@ExperimentalStdlibApi
class PinBoardViewModel(private val repository: PinRepository = PinRepository()) : ViewModel() {

    fun downloadThumb(pin: LoremPicksum) = pin.id?.let { id ->
        repository.getBitmap(Constants.getThumbUrl(id))
    }

    val progress = MutableLiveData(View.VISIBLE)

    val pinList = repository.getPins()

}
