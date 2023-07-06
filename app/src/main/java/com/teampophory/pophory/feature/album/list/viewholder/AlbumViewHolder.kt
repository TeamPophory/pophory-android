package com.teampophory.pophory.feature.album.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import com.teampophory.pophory.databinding.ItemHorizontalPhotoBinding
import com.teampophory.pophory.databinding.ItemVerticalPhotoBinding
import com.teampophory.pophory.feature.album.model.PhotoDetail
import com.teampophory.pophory.feature.album.model.PhotoItem

sealed class AlbumViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: PhotoItem)

    class HorizontalViewHolder(
        private val binding: ItemHorizontalPhotoBinding,
        private val onItemClicked: (PhotoDetail) -> Unit
    ) : AlbumViewHolder(binding) {
        override fun bind(item: PhotoItem) {
            if (item is PhotoItem.HorizontalItem) {
                binding.ivHorizontalImage.run {
                    load(item.photoDetail.imageUrl)
                    setOnClickListener {
                        onItemClicked(item.photoDetail)
                    }
                }
            }
        }
    }

    class VerticalViewHolder(
            private val binding: ItemVerticalPhotoBinding,
            private val onItemClicked: (PhotoDetail) -> Unit
    ) : AlbumViewHolder(binding) {
        override fun bind(item: PhotoItem) {
            with(binding) {
                if (item is PhotoItem.VerticalItem) {
                    val firstImageData = item.photoDetails.firstOrNull() ?: return
                    val secondImageData = item.photoDetails.getOrNull(1) ?: return

                    ivFirstVerticalImage.setOnClickListener {
                        onItemClicked(firstImageData)
                    }

                    ivSecondVerticalImage.setOnClickListener {
                        onItemClicked(secondImageData)
                    }
                    ivFirstVerticalImage.load(firstImageData.imageUrl)
                    ivSecondVerticalImage.load(secondImageData.imageUrl)
                }
            }
        }
    }
}