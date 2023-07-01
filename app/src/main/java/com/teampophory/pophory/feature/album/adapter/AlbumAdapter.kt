package com.teampophory.pophory.feature.album.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.databinding.ItemFlexboxLayoutBinding
import com.teampophory.pophory.feature.album.viewholder.FlexBoxLayoutViewHolder

class AlbumAdapter : ListAdapter<Int, FlexBoxLayoutViewHolder>(
    ItemDiffCallback<Int>(
        onItemsTheSame = { old, new -> old == new },
        onContentsTheSame = { old, new -> old == new }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlexBoxLayoutViewHolder {
        return FlexBoxLayoutViewHolder(
            ItemFlexboxLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FlexBoxLayoutViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}