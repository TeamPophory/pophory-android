package com.teampophory.pophory.feature.album.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.teampophory.pophory.databinding.ItemFlexboxLayoutBinding
import com.teampophory.pophory.feature.album.viewholder.FlexBoxLayoutViewHolder
import com.teampophory.pophory.util.DiffCallback

class AlbumAdapter : ListAdapter<Int, FlexBoxLayoutViewHolder>(
    DiffCallback<Int>(id = { old, new -> old == new })
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