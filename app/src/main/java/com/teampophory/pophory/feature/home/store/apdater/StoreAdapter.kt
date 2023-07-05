package com.teampophory.pophory.feature.home.store.apdater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.databinding.ItemStorePagerBinding
import com.teampophory.pophory.feature.home.store.model.AlbumItem

class StoreAdapter(
    private val onItemClicked: (AlbumItem) -> Unit,
    private val onPageChangedListener: OnPageChangedListener
) : ListAdapter<AlbumItem, StoreAdapter.StoreViewHolder>(
    ItemDiffCallback<AlbumItem>(
        onItemsTheSame = { old, new -> old.hashCode() == new.hashCode() },
        onContentsTheSame = { old, new -> old == new }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding =
            ItemStorePagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(binding, onItemClicked, onPageChangedListener)
    }

    class StoreViewHolder(
        private val binding: ItemStorePagerBinding,
        private val onItemClicked: (AlbumItem) -> Unit,
        private val onPageChangedListener: OnPageChangedListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(albumItem: AlbumItem) {
            itemView.setOnClickListener {
                onItemClicked(albumItem)
                onPageChangedListener.onPageChanged(albumItem)
            }
        }
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

interface OnPageChangedListener {
    fun onPageChanged(albumItem: AlbumItem)
}
