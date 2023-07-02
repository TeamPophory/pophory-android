package com.teampophory.pophory.feature.album.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.teampophory.pophory.databinding.ItemFlexboxLayoutBinding
import com.teampophory.pophory.network.model.PhotoListResponse

class FlexBoxLayoutViewHolder(
    private val binding: ItemFlexboxLayoutBinding
) : ViewHolder(binding.root) {

    fun bind(item: PhotoListResponse.Photo) {
        binding.ivFlexAlbum.load(item.imageUrl)
    }
}