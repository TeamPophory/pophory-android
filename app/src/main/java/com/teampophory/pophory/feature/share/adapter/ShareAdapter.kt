package com.teampophory.pophory.feature.share.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.databinding.ItemSharePhotoBinding
import com.teampophory.pophory.feature.share.model.PhotoItem

class ShareAdapter(
    private val onItemClicked: (PhotoItem, Int) -> Unit,
    private val onShareSheetDismissed: () -> Unit
) : ListAdapter<PhotoItem, ShareAdapter.ShareViewHolder>(
    ItemDiffCallback<PhotoItem>(
        onItemsTheSame = { old, new -> old.hashCode() == new.hashCode() },
        onContentsTheSame = { old, new -> old == new }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShareViewHolder {
        val binding =
            ItemSharePhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShareViewHolder(binding, onItemClicked)
    }

    class ShareViewHolder(
        val binding: ItemSharePhotoBinding,
        private val onItemClicked: (PhotoItem, Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photoItem: PhotoItem, position: Int) {
            binding.ivSharePhoto.load(photoItem.photoUrl)
            itemView.setOnClickListener {
                onItemClicked(photoItem,position)
                binding.ivSharePhotoSelected.isVisible = true
            }
        }
    }

    override fun onBindViewHolder(holder: ShareViewHolder, position: Int) {
        holder.bind(getItem(position),position)
    }

    override fun onViewRecycled(holder: ShareViewHolder) {
        super.onViewRecycled(holder)
        holder.binding.ivSharePhotoSelected.isVisible = false
        onShareSheetDismissed()
    }
}