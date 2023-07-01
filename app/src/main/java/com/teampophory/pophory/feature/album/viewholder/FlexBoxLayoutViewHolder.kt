package com.teampophory.pophory.feature.album.viewholder

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.teampophory.pophory.databinding.ItemFlexboxLayoutBinding

class FlexBoxLayoutViewHolder(
    private val binding: ItemFlexboxLayoutBinding
) : ViewHolder(binding.root) {

    fun bind(@DrawableRes id: Int) {
        binding.ivFlexAlbum.load(id)
    }
}