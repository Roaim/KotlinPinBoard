package com.roaim.kotlinpinboard.pinboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.roaim.kotlinpinboard.R
import com.roaim.kotlinpinboard.data.PinRepository
import com.roaim.kotlinpinboard.data.model.LoremPicksum
import com.roaim.kotlinpinboard.databinding.FragmentPinBoardBinding
import com.roaim.kotlinpinboard.utils.Constants
import com.roaim.kotlinpinboard.utils.PinViewModelFactory

/**
 * A placeholder fragment containing a simple view.
 */
@ExperimentalStdlibApi
class PinBoardFragment : Fragment(), PinClickListener {

    private val mViewModel by viewModels<PinBoardViewModel> { PinViewModelFactory(PinRepository()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentPinBoardBinding.inflate(inflater, container, false).also {
            it.viewModel = mViewModel
        }.run {
            pinList.adapter = PinAdapter(mViewModel).apply {
                setPinClickListener(this@PinBoardFragment)
            }
            lifecycleOwner = viewLifecycleOwner
            root
        }
    }

    override fun onPinClick(view: View, pin: LoremPicksum) {
        ViewCompat.setTransitionName(view, "rah")
        val navigatorExtras = FragmentNavigatorExtras(
            view to "rah"
        )
        view.findNavController().takeIf { it.currentDestination?.id == R.id.pinBoardFragment2 }
            ?.apply {
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
