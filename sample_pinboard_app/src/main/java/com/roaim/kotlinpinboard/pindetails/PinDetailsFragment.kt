package com.roaim.kotlinpinboard.pindetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import com.roaim.kotlinpinboard.data.PinRepository
import com.roaim.kotlinpinboard.databinding.PinDetailsFragmentBinding
import com.roaim.kotlinpinboard.utils.PinViewModelFactory

class PinDetailsFragment : Fragment() {

    private lateinit var binding: PinDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel by viewModels<PinDetailsViewModel> { PinViewModelFactory(PinRepository()) }
        return PinDetailsFragmentBinding.inflate(inflater, container, false).also {
            it.viewmodel = viewModel
            binding = it
        }.run {
            lifecycleOwner = this@PinDetailsFragment
            val safeArg = PinDetailsFragmentArgs.fromBundle(arguments!!)
            viewModel.setTitle(safeArg.title ?: "Something wrong!")
            viewModel.downloadImage(safeArg.imgUrl)
            viewModel.downloadImage(safeArg.thumbUrl)
            root
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(binding.frameLayout , "rah")
    }

}
