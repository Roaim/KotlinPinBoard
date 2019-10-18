package com.roaim.kotlinpinboard.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roaim.kotlinpinboard.data.PinRepository
import com.roaim.kotlinpinboard.pinboard.PinBoardViewModel
import com.roaim.kotlinpinboard.pindetails.PinDetailsViewModel

@ExperimentalStdlibApi
@Suppress("UNCHECKED_CAST")
class PinViewModelFactory(private val repository: PinRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(PinBoardViewModel::class.java) ->
                    PinBoardViewModel(repository)
                isAssignableFrom(PinDetailsViewModel::class.java) ->
                    PinDetailsViewModel(repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}