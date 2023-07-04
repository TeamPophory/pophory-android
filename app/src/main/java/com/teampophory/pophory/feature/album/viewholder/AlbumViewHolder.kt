package com.teampophory.pophory.feature.album.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import com.teampophory.pophory.databinding.ItemHorizontalPhotoBinding
import com.teampophory.pophory.databinding.ItemVerticalPhotoBinding
import com.teampophory.pophory.feature.album.model.PhotoItem

sealed class AlbumViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: PhotoItem)

    class HorizontalViewHolder(
        private val binding: ItemHorizontalPhotoBinding
    ) : AlbumViewHolder(binding) {
        override fun bind(item: PhotoItem) {
            if (item is PhotoItem.HorizontalItem) {
                binding.ivHorizontalImage.load(item.photo.imageUrl)
            }
        }
    }

    class VerticalViewHolder(
        private val binding: ItemVerticalPhotoBinding
    ) : AlbumViewHolder(binding) {
        override fun bind(item: PhotoItem) {
            with(binding) {
                if (item is PhotoItem.VerticalItem) {
                    val firstImageUrl = item.photos.firstOrNull() ?: return
                    val secondImageView = item.photos.getOrNull(1) ?: return
                    ivFirstVerticalImage.load(firstImageUrl)
                    ivSecondVerticalImage.load(secondImageView)
                }
            }
        }
    }
}