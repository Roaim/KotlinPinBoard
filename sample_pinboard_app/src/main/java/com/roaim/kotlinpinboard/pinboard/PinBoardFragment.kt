package com.roaim.kotlinpinboard.pinboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.roaim.kotlinpinboard.data.PinRepository
import com.roaim.kotlinpinboard.databinding.FragmentPinBoardBinding
import com.roaim.kotlinpinboard.utils.PinViewModelFactory

/**
 * A placeholder fragment containing a simple view.
 */
class PinBoardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel by viewModels<PinBoardViewModel> { PinViewModelFactory(PinRepository()) }
        return FragmentPinBoardBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
        }.run {
            pinList.adapter = PinAdapter(viewModel)
            lifecycleOwner = this@PinBoardFragment
            root
        }
    }
}
