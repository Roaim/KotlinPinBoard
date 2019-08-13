package com.roaim.kotlinpinboard.pinboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.roaim.kotlinpinboard.data.model.Pin
import com.roaim.kotlinpinboard.databinding.ItemPinBinding

class PinAdapter(private val viewModel: PinBoardViewModel) : PagedListAdapter<Pin, ViewHolder>(PinDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.attach()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.detach()
    }
}

class ViewHolder private constructor(private val binding: ItemPinBinding) :
    RecyclerView.ViewHolder(binding.root), LifecycleOwner {
    private val lifecycleReg = LifecycleRegistry(this)

    override fun getLifecycle(): Lifecycle {
        return lifecycleReg
    }

    init {
        lifecycleReg.currentState = Lifecycle.State.INITIALIZED
        binding.lifecycleOwner = this
    }

    fun attach() {
        lifecycleReg.currentState = Lifecycle.State.STARTED
    }

    fun detach() {
        lifecycleReg.currentState = Lifecycle.State.DESTROYED
    }

    fun bind(viewModel: PinBoardViewModel, item: Pin) {
        binding.pin = item
        viewModel.downloadThumb(item)
        val action =
            PinBoardFragmentDirections.actionPinBoardFragment2ToPinDetailsFragment2(item.id!!, item.urls?.regular!!)
        binding.frameThumb.setOnClickListener(Navigation.createNavigateOnClickListener(action))
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemPinBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class PinDiffCallback : DiffUtil.ItemCallback<Pin>() {
    override fun areItemsTheSame(oldItem: Pin, newItem: Pin): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pin, newItem: Pin): Boolean {
        return oldItem == newItem
    }
}