package com.teampophory.pophory.feature.album.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.teampophory.pophory.databinding.ItemFlexboxLayoutBinding

class FlexBoxLayoutViewHolder(
    private val binding: ItemFlexboxLayoutBinding
) : ViewHolder(binding.root) {

    fun bind(id: Int) {
        binding.ivFlexAlbum.load(id)
    }
}