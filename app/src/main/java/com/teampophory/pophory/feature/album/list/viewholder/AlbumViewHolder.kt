package com.teampophory.pophory.feature.album.list.viewholder

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import com.teampophory.pophory.R
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

                    ivFirstVerticalImage.run {
                        loadAndDisplayImage(firstImageData)
                        setOnClickListener {
                            onItemClicked(firstImageData)
                        }
                    }

                    ivSecondVerticalImage.run {
                        if (secondImageData.imageUrl.isNotBlank()) {
                            loadAndDisplayImage(secondImageData)
                            load(secondImageData.imageUrl) {
                                crossfade(true)
                                placeholder(R.drawable.img_loading_vertical)
                            }
                        } else {
                            load(R.drawable.img_default_vertical_1)
                        }

                        setOnClickListener {
                            onItemClicked(secondImageData)
                        }
                    }
                }
            }
        }

        private fun ImageView.loadAndDisplayImage(photoDetail: PhotoDetail) =
            load(photoDetail.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.img_loading_vertical)
            }
    }
}