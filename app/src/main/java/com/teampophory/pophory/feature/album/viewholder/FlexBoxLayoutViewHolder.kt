package com.teampophory.pophory.feature.album.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.teampophory.pophory.databinding.ItemFlexboxLayoutBinding
import com.teampophory.pophory.feature.album.model.PhotoList

class FlexBoxLayoutViewHolder(
    private val binding: ItemFlexboxLayoutBinding
) : ViewHolder(binding.root) {

    fun bind(item: PhotoList.Photo) {
        binding.ivFlexAlbum.load(item.imageUrl)
    }
}