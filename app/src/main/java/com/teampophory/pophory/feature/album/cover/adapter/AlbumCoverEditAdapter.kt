package com.teampophory.pophory.feature.album.cover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.databinding.ItemStorePagerBinding
import com.teampophory.pophory.feature.album.cover.model.AlbumCoverItem

class AlbumCoverEditAdapter : ListAdapter<AlbumCoverItem, AlbumCoverEditViewHolder>(
    ItemDiffCallback<AlbumCoverItem>(
        onItemsTheSame = { old, new -> old == new },
        onContentsTheSame = { old, new -> old == new },
    ),
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumCoverEditViewHolder {
        return AlbumCoverEditViewHolder(
            ItemStorePagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: AlbumCoverEditViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
