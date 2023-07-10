package com.teampophory.pophory.feature.home.store.apdater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampophory.pophory.R
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.databinding.ItemStorePagerBinding
import com.teampophory.pophory.feature.home.store.model.AlbumItem
import com.teampophory.pophory.util.toCoverDrawable

class StoreAdapter(
    private val onItemClicked: (AlbumItem) -> Unit,
) : ListAdapter<AlbumItem, StoreAdapter.StoreViewHolder>(
    ItemDiffCallback<AlbumItem>(
        onItemsTheSame = { old, new -> old.hashCode() == new.hashCode() },
        onContentsTheSame = { old, new -> old == new }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding =
            ItemStorePagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(binding, onItemClicked)
    }

    class StoreViewHolder(
        private val binding: ItemStorePagerBinding,
        private val onItemClicked: (AlbumItem) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(albumItem: AlbumItem) {
            binding.ivStorePager.setBackgroundResource(albumItem.albumCover.toCoverDrawable())
            itemView.setOnClickListener {
                onItemClicked(albumItem)
            }
        }
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}