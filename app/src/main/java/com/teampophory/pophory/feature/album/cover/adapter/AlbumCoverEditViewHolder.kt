package com.teampophory.pophory.feature.album.cover.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.teampophory.pophory.databinding.ItemStorePagerBinding
import com.teampophory.pophory.feature.album.cover.model.AlbumCoverItem

class AlbumCoverEditViewHolder(
    private val binding: ItemStorePagerBinding,
) : ViewHolder(binding.root) {
    fun bind(item: AlbumCoverItem) {
        binding.ivStorePager.setBackgroundResource(item.imageRes)
    }
}
