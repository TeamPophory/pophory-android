package com.teampophory.pophory.feature.home.store.apdater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampophory.pophory.databinding.ItemStorePagerBinding

class StoreAdapter(
    diffCallback: DiffUtil.ItemCallback<Int>,
    private val onItemClicked: (Int) -> Unit,
) : ListAdapter<Int, StoreAdapter.StoreViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding =
            ItemStorePagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(binding, onItemClicked)
    }

    class StoreViewHolder(
        private val binding: ItemStorePagerBinding,
        private val onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(@DrawableRes src: Int) {
            binding.ivStorePager.setImageResource(src)
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
