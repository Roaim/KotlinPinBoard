package com.roaim.kotlinpinboard.pinboard

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.roaim.kotlinpinboard.data.model.LoremPicksum
import com.roaim.kotlinpinboard.databinding.ItemPinBinding
import com.roaim.kotlinpinboard.utils.observeOnce

class PinAdapter(private val viewModel: PinBoardViewModel) : PagedListAdapter<LoremPicksum, ViewHolder>(PinDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, viewModel)
    }
}

class ViewHolder private constructor(private val binding: ItemPinBinding, private val viewModel: PinBoardViewModel) :
    RecyclerView.ViewHolder(binding.root) {

    private var liveData: LiveData<Bitmap?>? = null
    private var liveDataObserver: Observer<Bitmap?>? = null

    fun bind(item: LoremPicksum) {
        liveData?.takeIf { it.hasObservers() }?.run {
            removeObserver(liveDataObserver!!)
            binding
        }
        with(binding) {
            pin = item
            ivThumb.setImageBitmap(null)
            viewModel.downloadThumb(item)?.also { ld -> liveData = ld }?.observeOnce(Observer<Bitmap?> {
                binding.ivThumb.setImageBitmap(it)
            }.also {
                liveDataObserver = it
            })
        }
    }

    companion object {
        fun from(parent: ViewGroup, viewModel: PinBoardViewModel): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ItemPinBinding.inflate(layoutInflater, parent, false).run {
                vm = viewModel
                ViewHolder(this, viewModel)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class PinDiffCallback : DiffUtil.ItemCallback<LoremPicksum>() {
    override fun areItemsTheSame(oldItem: LoremPicksum, newItem: LoremPicksum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LoremPicksum, newItem: LoremPicksum): Boolean {
        return oldItem == newItem
    }
}