package com.teampophory.pophory.feature.album.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.databinding.ItemHorizontalPhotoBinding
import com.teampophory.pophory.databinding.ItemVerticalPhotoBinding
import com.teampophory.pophory.feature.album.model.PhotoItem
import com.teampophory.pophory.feature.album.viewholder.AlbumViewHolder

class AlbumAdapter : ListAdapter<PhotoItem, AlbumViewHolder>(
    ItemDiffCallback<PhotoItem>(
        onItemsTheSame = { old, new -> old.hashCode() == new.hashCode() },
        onContentsTheSame = { old, new -> old == new }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return when (viewType) {
            AlbumViewType.HORIZONTAL_TYPE.ordinal -> {
                AlbumViewHolder.HorizontalViewHolder(
                    ItemHorizontalPhotoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                AlbumViewHolder.VerticalViewHolder(
                    ItemVerticalPhotoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item is PhotoItem.HorizontalItem) {
            AlbumViewType.HORIZONTAL_TYPE.ordinal
        } else {
            AlbumViewType.VERTICAL_TYPE.ordinal
        }
    }

    enum class AlbumViewType {
        HORIZONTAL_TYPE,
        VERTICAL_TYPE
    }
}