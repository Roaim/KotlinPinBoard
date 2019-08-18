package com.roaim.kotlinpinboard.pinboard

import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.roaim.kotlinpinboard.R
import com.roaim.kotlinpinboard.data.PinRepository
import com.roaim.kotlinpinboard.data.model.LoremPicksum
import com.roaim.kotlinpinboard.utils.Constants

class PinBoardViewModel(private val repository: PinRepository = PinRepository()) : ViewModel() {

    fun downloadThumb(pin: LoremPicksum) = pin.id?.let { id ->
        repository.getBitmap(Constants.getThumbUrl(id))
    }

    val progress = MutableLiveData(View.VISIBLE)

    val pinList = repository.getPins()

    fun onPinClick(view: View, pin: LoremPicksum) {
        ViewCompat.setTransitionName(view, "rah")
        val navigatorExtras = FragmentNavigatorExtras(
            view to "rah"
        )
        view.findNavController().takeIf { it.currentDestination?.id == R.id.pinBoardFragment2 }?.apply {
            PinBoardFragmentDirections.actionPinBoardFragment2ToPinDetailsFragment2(
                pin.author!!,
                Constants.getImageUrl(pin.id!!),
                Constants.getThumbUrl(pin.id!!)
            ).also {
                navigate(it, navigatorExtras)
            }
        }
    }

}
