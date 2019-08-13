package com.roaim.kotlinpinboard.pindetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.roaim.kotlinpinboard.data.PinRepository
import com.roaim.kotlinpinboard.databinding.PinDetailsFragmentBinding
import com.roaim.kotlinpinboard.utils.PinViewModelFactory

class PinDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel by viewModels<PinDetailsViewModel> { PinViewModelFactory(PinRepository()) }
        return PinDetailsFragmentBinding.inflate(inflater, container, false).also {
            it.viewmodel = viewModel
        }.run {
            lifecycleOwner = this@PinDetailsFragment
            val safeArg = PinDetailsFragmentArgs.fromBundle(arguments!!)
            viewModel.setTitle(safeArg.title ?: "Something wrong!")
            viewModel.downloadImage(safeArg.imgUrl)
            root
        }
    }

}
