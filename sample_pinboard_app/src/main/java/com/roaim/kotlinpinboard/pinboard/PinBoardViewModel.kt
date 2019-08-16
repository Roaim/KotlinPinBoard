package com.roaim.kotlinpinboard.pinboard

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.roaim.kotlinpinboard.R
import com.roaim.kotlinpinboard.data.PinRepository
import com.roaim.kotlinpinboard.data.model.LoremPicksum
import com.roaim.kotlinpinboard.utils.Constants

class PinBoardViewModel(private val repository: PinRepository = PinRepository()) : ViewModel() {

    fun downloadThumb(pin: LoremPicksum) = pin.id?.let { id ->
        repository.getBitmap(Constants.getThumbUrl(id))
    }

    val pinList = repository.getPins()

    fun onPinClick(view: View, pin: LoremPicksum) {
        view.findNavController().takeIf { it.currentDestination?.id == R.id.pinBoardFragment2 }?.apply {
            PinBoardFragmentDirections.actionPinBoardFragment2ToPinDetailsFragment2(
                pin.author!!,
                Constants.getImageUrl(pin.id!!)
            ).also {
                navigate(it)
            }
        }
    }

}
