package com.teampophory.pophory.feature.album.list.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.databinding.ItemHorizontalPhotoBinding
import com.teampophory.pophory.databinding.ItemVerticalPhotoBinding
import com.teampophory.pophory.feature.album.detail.AlbumDetailActivity
import com.teampophory.pophory.feature.album.list.viewholder.AlbumViewHolder
import com.teampophory.pophory.feature.album.model.OrientType
import com.teampophory.pophory.feature.album.model.PhotoDetail
import com.teampophory.pophory.feature.album.model.PhotoItem

class AlbumListAdapter : ListAdapter<PhotoItem, AlbumViewHolder>(
    ItemDiffCallback<PhotoItem>(
        onItemsTheSame = { old, new -> old == new },
        onContentsTheSame = { old, new -> old == new }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return when (viewType) {
            AlbumViewType.HORIZONTAL_TYPE.ordinal -> {
                val binding = ItemHorizontalPhotoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AlbumViewHolder.HorizontalViewHolder(
                    binding,
                    onItemClicked = {
                        if (it.orientType == OrientType.NONE) return@HorizontalViewHolder
                        startTransitionActivity(parent.context, binding.ivHorizontalImage, it)
                    }
                )
            }

            else -> {
                val binding = ItemVerticalPhotoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AlbumViewHolder.VerticalViewHolder(
                    binding,
                    onItemClicked = { pair ->
                        if (pair.second.orientType == OrientType.NONE) return@VerticalViewHolder
                        val animationImageView = when (pair.first) {
                            AlbumViewHolder.VerticalItemType.FIRST -> binding.ivFirstVerticalImage
                            AlbumViewHolder.VerticalItemType.SECOND -> binding.ivSecondVerticalImage
                        }
                        startTransitionActivity(parent.context, animationImageView, pair.second)
                    }
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

    private fun startTransitionActivity(
        context: Context,
        imageView: ImageView,
        photoDetail: PhotoDetail
    ) {
        val activity = context as? Activity ?: return
        val transitionAnimation =
            ActivityOptions.makeSceneTransitionAnimation(activity, imageView, "thumb").toBundle()
        val intent = AlbumDetailActivity.newIntent(context, photoDetail)
        context.startActivity(intent, transitionAnimation)
    }

    enum class AlbumViewType {
        HORIZONTAL_TYPE,
        VERTICAL_TYPE
    }
}